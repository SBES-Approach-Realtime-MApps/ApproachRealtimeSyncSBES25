# Test Instances

Para atestar a replicação, iremos usar os endpoint prontos com comandos no shell para adicionar, inspecionar e deletar instâncias a serem propagadas entre as bases de dados. 

Para tal, é importante explicitar que o estudo de caso no nosso docker compose está configurado da seguinte maneira, afim de confirmar a origem e destino das URLs:

- MSCondominium: expose port 8080
- MSResident: expose port 8081
- MSReservation: expose port 8082
- MSReplicator: expose port 8084

## Resident Example

Inicialmente para confirmar a execução da replicação, vamos conferir se todos os bancos de dados estão vazios e não possuem moradores cadastrados. Primeiro, com todos os containers gerenciados pelo docker compose, vamos aferir que o banco de dados original do microsserviço de moradores está vazio utilizando um endpoint de busca geral.

Endpoint de busca geral de moradores para <b>MSResident</b>:

    curl -X GET http://localhost:8081/resident/findAll \ 
       -H "Content-Type: application/json"

A resposta desta requisição deve ser uma lista JSON vazia, como: `[]`

Os moradores persistidos na base de dados dos moradores (MongoDB) são transmitidos para as bases do condomínio (Postgres) e reserva (MariaDB). Vamos confirmar que essas bases de dados também estão vazias realizando uma busca geral novamente para os outros microsserviços, utilizando seus respectivos endpoints.

Endpoint de busca geral de moradores para <b>MSCondominium</b>:

    curl -X GET http://localhost:8080/resident/findAll \
       -H "Content-Type: application/json"

Endpoint de busca geral de moradores para <b>MSReservation</b>:

    curl -X GET http://localhost:8082/resident/findAll \
       -H "Content-Type: application/json"

Ambos os endpoints também devem retornar listas JSON vazias.

### Testando inserção

Após aferir que essas bases de dados estão vazias, vamos adicionar um morador para podermos visualizarmos a replicação deste dado para as outras bases. Usemos o endpoint de inserção do microsserviço de moradores.

Endpoint de inserção de moradores para <b>MSResident</b>:

    curl -X POST http://localhost:8081/resident/save \    
       -H "Content-Type: application/json" \
       -d '{
            "name": "John Due",
            "birthDate": "2003-05-19",
            "contact": {
                "homePhone": 9999955178,
                "businessPhone": "994512313",
                "cellPhone": "997502563",
                "email": "JonhDue@gmail.com"
            },
            "address": [
                {
                    "street": "Very Crazy Street",
                    "number": 770,
                    "neighborhood": "New Random Neghborhood",
                    "city": "Washington",
                    "state": "Washington DC",
                    "zipCode": "15154646513"
                },
                {
                    "street": "Av 13",
                    "number": 157,
                    "neighborhood": "Centro",
                    "city": "Santa Gertrudes",
                    "state": "São Paulo",
                    "zipCode": "13510000"
                }
            ],
            "residentType": "OWNER"
        }'

A resposta desse endpoint deve ser a entidade criada no banco de dados, Retornando algo como o exemplo de reposta abaixo, normalmente sem nenhum tipo de formatação:

`{  "id": "680b994a935da176e73f6389",
            "name": "John Due",
            "birthDate": "2003-05-19",
            "contact": {
                "homePhone": 9999955178,
                "businessPhone": "994512313",
                "cellPhone": "997502563",
                "email": "JonhDue@gmail.com"
            },
            "address": [
                {
                    "street": "Very Crazy Street",
                    "number": 770,
                    "neighborhood": "New Random Neghborhood",
                    "city": "Washington",
                    "state": "Washington DC",
                    "zipCode": "15154646513"
                },
                {
                    "street": "Av 13",
                    "number": 157,
                    "neighborhood": "Centro",
                    "city": "Santa Gertrudes",
                    "state": "São Paulo",
                    "zipCode": "13510000"
                }
            ],
            "residentType": "OWNER"
        }`

Note que foi devolvido um campo a mais com o nome <i>id</i>, que representa o identificador que o Spring Data MongoDB cria ao persistir um objeto.

Vamos verificar se ele foi adicionado a base de dados de <b>MSResident</b>, podemos utilizar ou o endpoint de busca geral que já mostramos ou o endpoint de busca por id.

Endpoint de busca específica de moradores de <b>MSResident</b>

    curl -X GET http://localhost:8081/resident/findById/putYourIdHere \ 
       -H "Content-Type: application/json"

Ou use a busca geral:

    curl -X GET http://localhost:8081/resident/findAll \
       -H "Content-Type: application/json"

A resposta deste endpoint deve conter o mesmo dado da resposta do endpoint da inserção, com a única diferença que a busca geral retornará o objeto dentro de uma lista JSON (`[{object}]`). Após confirmar que o objeto foi criado nessa base de dados,vamos aferir se a propagação funcionou procurando nas bases dos outros microsserviços.

Endpoint de busca específica de moradores de <b>MSCondominium</b>:

    curl -X GET http://localhost:8080/resident/findById/putYourIdHere \
       -H "Content-Type: application/json"

Ou use a busca geral:

    curl -X GET http://localhost:8080/resident/findAll \
       -H "Content-Type: application/json"

O retorno deve ser algo parecido com:

`{"id":"680b994a935da176e73f6389","name":"John Due","birthDate":"2003-05-19T00:00:00.000+00:00","residentType":"OWNER"}`

Aqui existe uma diferença relevante no retorno, note como a entidade foi transformada para conter apenas os dados pertinentes ao microsserviço de condomínios, informações como email, senha, endereços e contato não existem mais. Idem para o microsserviço de reserva

Endpoint de busca específica de moreadores de <b>MSReservation</b>:

    curl -X GET http://localhost:8082/resident/findById/putYourIdHere \       
       -H "Content-Type: application/json"

Ou use a busca geral:

    curl -X GET http://localhost:8082/resident/findAll \
       -H "Content-Type: application/json"

O retorno deve ser algo parecido com: 

`{"id":"680b994a935da176e73f6389","name":"Daniel de Almeida","birthDate":"2003-05-19T00:00:00.000+00:00","residentType":"OWNER"}`

Note que, tanto em MSReservation como em MSCondominium foram adicionados às respectivas bases de dados o morador inserido ainda que eles não tenham endpoints para inserção e nem MSResidente possui uma instrução direta para salvar em outras bases que não seja a sua própria. A replicação foi feita pela nossa abordagem a nível de base de dados.

### Testando atualização

Vamos agora testar atualizar a entidade morador que criamos na base de dados original e ver como essas mudanças se refletem na base de dados dos microsserviços, por razões óbvias, as alterações precisam ser de atributos que constam nas outras bases de dados ou que são derivados destes.

Endpoint de atualização de moradores para <b>MSResident</b>:

    curl -X PUT http://localhost:8081/resident/update \
       -H "Content-Type: application/json" \
       -d '{
            {
                "id": "putYourIdHere",
                "name": "John Due Two",     
                "residentType": "TENANT"
            }
        }'

Essa requisição retornará a entidade atualizada, sendo parecidíssima com o retorno da busca específica, já com os campos modificados. Vale lembrar que já é assim que a entidade está na base de dados, portanto, pularemos a etapa de busca em <b>MSResident</b>, mas você pode repeti-la se quiser. 

O retorno dessa requisição pode ser algo como:

`{"id":"680b994a935da176e73f6389","name":"John Due Two","birthDate":"2003-05-19T00:00:00.000+00:00","contact":{"homePhone":null,"businessPhone":"994512313","cellPhone":"997502563","email":"daniel.almeida19@unesp.br"},"address":[{"street":"Rua irma Diva Patarra","number":"770","neighborhood":"Jardim Ipiratininga","city":"Araras","state":"São Paulo","zipCode":"13604065"},{"street":"Av 13","number":"157","neighborhood":"Centro","city":"Santa Gertrudes","state":"São Paulo","zipCode":"13510000"}],"residentType":"TENANT"}`

Note como o nome e o tipo de morador já foram atualizados na base de dados original. Vamos verificar agora a replicação da atualização da base de dados, procurando pelo nosso morador nos outros microsserviços, usando os endpoints que já vimos antes.

Use a busca específica de moradores de <b>MSCondominium</b>:

    curl -X GET http://localhost:8080/resident/findById/putYourIdHere \
       -H "Content-Type: application/json"

Ou use a busca geral:

    curl -X GET http://localhost:8080/resident/findAll \
       -H "Content-Type: application/json"

O retorno da requisição será algo como: 

`{"id":"680b994a935da176e73f6389","name":"John Due Two","birthDate":"2003-05-19T00:00:00.000+00:00","residentType":"TENANT"}`

Note que a atualização de nome e do tipo de morador já foi feita para a base de dados de <b>MSCondominium</b>. O mesmo teste pode ser feito para <b>MSReservation</b>

Use a busca específica de moreadores de <b>MSReservation</b>:

    curl -X GET http://localhost:8082/resident/findById/putYourIdHere \       
       -H "Content-Type: application/json"

Ou use a busca geral:

    curl -X GET http://localhost:8082/resident/findAll \
       -H "Content-Type: application/json"

O retorno deve ser algo parecido com: 

`{"id":"680b994a935da176e73f6389","name":"John Due Two","birthDate":"2003-05-19T00:00:00.000+00:00","residentType":"TENANT"}`


### Testando deleção

Vamos agora testar a deleção de um morador na base de dados original e ver como essa mudança reflete na base de dados dos outros microsserviços.

Endpoint de deleção de moradores de <b>MSResident</b>:

    curl -X DELETE http://localhost:8081/resident/delete/putYourIdHere \
        -H "Content-Type: application/json"

Esta requisição não gerará nenhum retorno. Vamos averiguar se a base de dados original realmente deixou de conter o morador que continha anteriormente.

Use o endpoint de busca geral de moradores para MSResident:

    curl -X GET http://localhost:8081/resident/findAll \
        -H "Content-Type: application/json"

A resposta deste endpoint deve ser lista vazia: `[]`

Vamos conferir agora se a deleção foi propagada para as bases de dados dos outros microsserviços.

Use o endpoint de busca geral de moradores de <b>MSCondominium</b>:

    curl -X GET http://localhost:8080/resident/findAll \
       -H "Content-Type: application/json"

A resposta deste endpoint deve ser lista vazia: `[]`

Use o endpoint de busca geral de moradores de <b>MSReservation</b>:

    curl -X GET http://localhost:8082/resident/findAll \
       -H "Content-Type: application/json"

A resposta deste endpoint deve ser lista vazia: `[]`

Note que, em ambos os casos a deleção foi propaga a nível de base de dados pela nossa abordagem.