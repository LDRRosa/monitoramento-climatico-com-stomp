package com.metereologia.monitoramento.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.metereologia.monitoramento.model.Cidade;

@Service
public class ClimaService {

    private final SimpMessagingTemplate messagingTemplate;
    private final RestTemplate restTemplate = new RestTemplate();
    private final Random random = new Random();

    private final List<Cidade> cidades = Arrays.asList(
            new Cidade("Vianópolis", -16.74, -48.51),
            new Cidade("Goiânia", -16.68, -49.25),
            new Cidade("São Paulo", -23.55, -46.63),
            new Cidade("Curitiba", -25.42, -49.27),
            new Cidade("Manaus", -3.11, -60.02),
            new Cidade("Salvador", -12.97, -38.50),
            new Cidade("Fortaleza", -3.71, -38.54),
            new Cidade("Cuiabá", -15.60, -56.09),
            new Cidade("Porto Alegre", -30.03, -51.22),
            new Cidade("Brasília", -15.79, -47.88));

    public ClimaService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void atualizarClima() {
        Cidade cidadeSorteada = cidades.get(random.nextInt(cidades.size()));

        String url = String.format(Locale.US,
                "https://api.open-meteo.com/v1/forecast?latitude=%.2f&longitude=%.2f&current_weather=true",
                cidadeSorteada.lat,
                cidadeSorteada.lon);

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            Map<String, Object> currentWeather = (Map<String, Object>) response.get("current_weather");

            System.out.println("JSON recebido da API: " + response);

            Map<String, Object> payload = new HashMap<>();
            payload.put("cidade", cidadeSorteada.nome);
            payload.put("temperatura", currentWeather.get("temperature"));

            Number codeNum = (Number) currentWeather.get("weathercode");
            payload.put("descricao", traduzirCodigoClima(codeNum.intValue()));

            payload.put("horario", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

            messagingTemplate.convertAndSend("/topic/clima", (Object) payload);

            System.out.println("Enviado: " + cidadeSorteada.nome + " " + currentWeather.get("temperature") + "°C");

        } catch (Exception e) {
            System.err.println("Erro ao buscar clima de " + cidadeSorteada.nome + ": " + e.getMessage());
        }
    }

    private String traduzirCodigoClima(int code) {
        if (code == 0)
            return "Céu Limpo";
        if (code >= 1 && code <= 3)
            return "Parcialmente Nublado";
        if (code >= 45 && code <= 48)
            return "Nevoeiro";
        if (code >= 51 && code <= 67)
            return "Drizzle/Chuva Leve";
        if (code >= 71 && code <= 77)
            return "Neve";
        if (code >= 80 && code <= 82)
            return "Pancadas de Chuva";
        if (code >= 95)
            return "Tempestade";
        return "Desconhecido (" + code + ")";
    }
}