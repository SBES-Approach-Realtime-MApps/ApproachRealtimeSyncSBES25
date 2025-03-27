# Documentação do Estudo de Caso
Local de publicação - Nome do artigo - 2025</br>
Daniel</br>
Frank</br>
Fernando</br>
Orlando</br>

## Setup
Obter os arquivos do nosso repositório com:</br>

`git clone https://github.com/DanielAlmeida19/CaseStudyArticleSBES.git`</br>

Primeiro, para inicializar as aplicações de messageria, compartilhamento dos dados e as aplicações dos banco de dados em si, é necessário rodas os <i>containers</i> configurados pelo docker-compose presente no repositório:</br>

`docker-compose up -d # Rodar em segundo plano para aproveitar o terminal`</br>

Com as aplicações corretamente inicializadas, rodar as apliacções spring:

- Para a aplicação que gerencia os moradores:

        ./MSResident/mvnw spring-boot:run

- Para a aplicação que gerencia as reservas:

        ./MSReservation/mvnw spring-boot:run

- Para a aplicação  que gerencia os condomínios e unidades:

        ./MSCondominium/mvnw spring-boot:run