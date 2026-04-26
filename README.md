🌡️ Monitor de Temperatura em Tempo Real (WebSockets)
📌 Sobre o Projeto

Este projeto demonstra uma implementação de monitoramento climático em tempo real utilizando Spring Boot, WebSockets (STOMP + SockJS) e a API pública Open-Meteo.

O sistema realiza o broadcast de temperaturas de 10 cidades brasileiras a cada 5 segundos, exibindo os dados em um dashboard interativo e dinâmico.

🚀 Tecnologias Utilizadas
☕ Java 21
🌱 Spring Boot 3.x
🔌 Spring WebSocket (STOMP & SockJS)
🌍 Open-Meteo API (dados climáticos gratuitos)
🎨 Vanilla JavaScript & CSS3
🛠️ Como Rodar o Projeto
📋 Pré-requisitos
JDK 21 instalado
Maven 3.x ou superior
▶️ Passo a Passo
1. Clone o repositório
git clone https://github.com/seu-usuario/monitoramento-clima.git
cd monitoramento-clima
2. Execute a aplicação
mvn spring-boot:run
3. Acesse o dashboard

Abra o navegador e acesse:

http://localhost:8080/index.html
🔄 Fluxo de Mensagens

O sistema segue um modelo assíncrono baseado em eventos. Veja o fluxo completo:

⏱️ 1. Agendamento (@Scheduled)

A cada 5 segundos, o servidor:

Sorteia uma cidade de uma lista pré-definida
Utiliza suas coordenadas (latitude/longitude)
🌐 2. Consumo da API
Utiliza RestTemplate para consumir a API Open-Meteo
Força Locale.US para garantir formatação correta das coordenadas
⚙️ 3. Processamento
Extrai:
Temperatura
weathercode
Converte o código em descrição amigável (ex: "Céu Limpo")
📡 4. Broadcast (STOMP)
O servidor envia os dados via WebSocket para:
/topic/clima
💻 5. Recepção no Cliente
O navegador recebe os dados via SockJS
Atualiza dinamicamente o card da cidade
Aplica cores baseadas na temperatura:
🔴 > 25°C → Vermelho
🔵 ≤ 25°C → Azul
📁 Estrutura de Pastas
src/
 └── main/
     ├── java/.../
     │   ├── model/
     │   │   └── Cidades.java
     │   ├── services/
     │   │   └── ClimaService.java
     │   └── config/
     │       └── WebSocketConfig.java
     │
     └── resources/
         └── static/
             └── index.html
📝 Notas de Implementação
🌍 CORS

O servidor está configurado para aceitar requisições de qualquer origem, facilitando testes locais.

🎨 Estilização
Utiliza CSS Grid
Layout responsivo adaptável a diferentes resoluções
🌐 Localização (Locale)
Uso de Locale.US na montagem da URL da API
Evita erros 400 Bad Request causados por vírgulas no padrão PT-BR
💡 Possíveis Melhorias
📊 Adicionar gráficos históricos de temperatura
🌎 Permitir seleção dinâmica de cidades
🔔 Sistema de alertas climáticos
☁️ Deploy em cloud (AWS, Render, etc.)
📄 Licença

Este projeto é livre para uso acadêmico e aprendizado.
