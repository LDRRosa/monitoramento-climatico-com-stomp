🌡️ Monitor de Temperatura em Tempo Real (WebSockets)
Este projeto demonstra uma implementação de monitoramento climático utilizando Spring Boot, WebSockets (STOMP + SockJS) e a API pública Open-Meteo. O sistema realiza o broadcast de temperaturas de 10 cidades brasileiras em intervalos de 5 segundos para um dashboard interativo.
----
🚀 Tecnologias Utilizadas

Java 21

Spring Boot 3.x

Spring WebSocket (STOMP & SockJS)

Open-Meteo API (Dados climáticos gratuitos)

Vanilla JS & CSS3 (Front-end)
-----
🛠️ Como Rodar o Projeto
Pré-requisitos
JDK 21 instalado.

Maven 3.x ou superior.

Passo a Passo
Clone o repositório:

Bash
git clone https://github.com/seu-usuario/monitoramento-clima.git
cd monitoramento-clima
Compile e execute a aplicação:

Bash
mvn spring-boot:run
Acesse o Dashboard:
Abra o seu navegador e acesse:
http://localhost:8080/index.html
------
🔄 Fluxo de Mensagens
O projeto segue um fluxo de comunicação assíncrona baseada em eventos. Abaixo, a explicação detalhada de cada etapa:

Agendamento (@Scheduled): A cada 5 segundos, o servidor Spring Boot sorteia uma cidade de uma lista pré-definida com suas respectivas coordenadas (Latitude/Longitude).

Consumo de API: O serviço utiliza o RestTemplate para buscar o JSON atualizado da API Open-Meteo, forçando o Locale.US para garantir que as coordenadas sejam enviadas com ponto decimal.

Processamento: O servidor extrai a temperatura e o weathercode (convertendo o código numérico para uma descrição amigável como "Céu Limpo").

Broadcast (STOMP): O objeto de dados é convertido em JSON e enviado para o tópico /topic/clima.

Recepção no Cliente: O navegador, conectado via SockJS, recebe o payload e identifica o card da cidade através do ID do elemento, atualizando apenas os valores necessários e alterando a cor do card (Vermelho para > 25°C, Azul para ≤ 25°C).
------
📁 Estrutura de Pastas Principal
src/main/java/.../model/Cidades.java: Classe POJO para representar os dados das cidades.

src/main/java/.../services/ClimaService.java: Lógica de consumo da API e disparo do WebSocket.

src/main/java/.../config/WebSocketConfig.java: Configuração do Broker e Endpoints.

src/main/resources/static/index.html: Interface do dashboard com lógica de atualização em tempo real.
------
📝 Notas de Implementação
CORS: O servidor está configurado para aceitar conexões de qualquer origem para facilitar testes locais.

Estilização: O dashboard utiliza CSS Grid para responsividade, garantindo que os cards se ajustem a diferentes resoluções de tela.

Localização: A formatação da URL da API utiliza Locale.US para evitar erros de 400 Bad Request causados pelo uso de vírgulas em sistemas configurados em Português (PT-BR).
