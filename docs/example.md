# Test Instances

To verify replication, we will use ready-made endpoints with shell commands to add, inspect, and delete instances to be propagated between the databases.

For this, it is important to clarify that the case study in our Docker Compose is configured as follows, in order to confirm the source and destination of the URLs:

- MSCondominium: expose port 8080
- MSResident: expose port 8081
- MSReservation: expose port 8082
- MSReplicator: expose port 8084

## Resident Example

Initially, to confirm the replication execution, we will check if all databases are empty and have no registered residents. First, with all containers managed by Docker Compose, we will verify that the original database of the resident microservice is empty using a general search endpoint.

General search endpoint for residents in <b>MSResident</b>:

    curl -X GET http://localhost:8081/resident/findAll \
       -H "Content-Type: application/json"

The response to this request should be an empty JSON list, like: `[]`

Residents persisted in the resident service database (MongoDB) are transmitted to the condominium (Postgres) and reservation (MariaDB) databases. We will confirm that these databases are also empty by performing a general search again for the other microservices, using their respective endpoints.

General search endpoint for residents in <b>MSCondominium</b>:

    curl -X GET http://localhost:8080/resident/findAll \
       -H "Content-Type: application/json"

General search endpoint for residents in <b>MSReservation</b>:

    curl -X GET http://localhost:8082/resident/findAll \
       -H "Content-Type: application/json"

Both endpoints should also return empty JSON lists.

### Testing Insertion

After verifying that these databases are empty, we will add a resident so that we can observe the replication of this data to the other databases. Let's use the insertion endpoint of the resident microservice.

Resident insertion endpoint for <b>MSResident</b>:

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

The response from this endpoint should be the entity created in the database, returning something like the example below, usually without any formatting:

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

Notice that an additional field called <i>id</i> has been returned, which represents the identifier that Spring Data MongoDB creates when persisting an object.

Let's verify if it was added to the <b>MSResident</b> database. We can use either the general search endpoint we have already shown or the search-by-id endpoint.

Specific search endpoint for residents in <b>MSResident</b>:

    curl -X GET http://localhost:8081/resident/findById/putYourIdHere \
       -H "Content-Type: application/json"

Or use the general search:

    curl -X GET http://localhost:8081/resident/findAll \
       -H "Content-Type: application/json"

The response from this endpoint should contain the same data as the insertion endpoint response, with the only difference being that the general search will return the object inside a JSON list (`[{object}]`). After confirming that the object was created in this database, let's verify if the propagation worked by searching in the databases of the other microservices.

Specific search endpoint for residents in <b>MSCondominium</b>:

    curl -X GET http://localhost:8080/resident/findById/putYourIdHere \
       -H "Content-Type: application/json"

Or use the general search:

    curl -X GET http://localhost:8080/resident/findAll \
       -H "Content-Type: application/json"

The return should be something like:

`{"id":"680b994a935da176e73f6389","name":"John Due","birthDate":"2003-05-19T00:00:00.000+00:00","residentType":"OWNER"}`

There is an important difference in the return here: notice how the entity was transformed to contain only the data relevant to the condominium microservice. Information such as email, password, addresses, and contact details no longer exist. The same applies to the reservation microservice.

Specific search endpoint for residents in <b>MSReservation</b>:

    curl -X GET http://localhost:8082/resident/findById/putYourIdHere \
       -H "Content-Type: application/json"

Or use the general search:

    curl -X GET http://localhost:8082/resident/findAll \
       -H "Content-Type: application/json"

The return should be something like:

`{"id":"680b994a935da176e73f6389","name":"John Due","birthDate":"2003-05-19T00:00:00.000+00:00","residentType":"OWNER"}`

Note that, in both MSReservation and MSCondominium, the resident was added to their respective databases even though they do not have insertion endpoints and MSResident has no direct instruction to save data to databases other than its own. Replication was handled by our database-level approach.


### Testing Update

Now let's test updating the resident entity we previously created in the original database and see how these changes are reflected in the microservices' databases. Obviously, the updates must affect attributes that are present in the other databases or that are derived from them.

Update resident endpoint for <b>MSResident</b>:

    curl -X PUT http://localhost:8081/resident/update \
       -H "Content-Type: application/json" \
       -d '{
                "id": "putYourIdHere",
                "name": "John Due Two",     
                "residentType": "TENANT"
        }'

This request will return the updated entity, very similar to the specific search response, but now reflecting the modified fields. Remember that this is already how the entity is saved in the database, so we will skip the search step in <b>MSResident</b> — although you can repeat it if you wish.

The response to this request might look like:

`{"id":"680b994a935da176e73f6389","name":"John Due Two","birthDate":"2003-05-19T00:00:00.000+00:00","contact":{"homePhone":null,"businessPhone":"994512313","cellPhone":"997502563","email":"JonhDue@gmail.com"},"address":[{"street":"Very Crazy Street","number":"770","neighborhood":"New Random Neghborhood","city":"Washington","state":"Washington DC","zipCode":"15154646513"},{"street":"Av 13","number":"157","neighborhood":"Centro","city":"Santa Gertrudes","state":"São Paulo","zipCode":"13510000"}],"residentType":"TENANT"}`

Notice how the name and resident type have already been updated in the original database. Now let's check the replication of this update across the microservices' databases by searching for our resident using the endpoints we previously saw.

Use the specific search for residents in <b>MSCondominium</b>:

    curl -X GET http://localhost:8080/resident/findById/putYourIdHere \
       -H "Content-Type: application/json"

Or use the general search:

    curl -X GET http://localhost:8080/resident/findAll \
       -H "Content-Type: application/json"

The response should look like:

`{"id":"680b994a935da176e73f6389","name":"John Due Two","birthDate":"2003-05-19T00:00:00.000+00:00","residentType":"TENANT"}`

Notice that the name and resident type have already been updated in the <b>MSCondominium</b> database. The same test can be done for <b>MSReservation</b>.

Use the specific search for residents in <b>MSReservation</b>:

    curl -X GET http://localhost:8082/resident/findById/putYourIdHere \
       -H "Content-Type: application/json"

Or use the general search:

    curl -X GET http://localhost:8082/resident/findAll \
       -H "Content-Type: application/json"

The response should look like:

`{"id":"680b994a935da176e73f6389","name":"John Due Two","birthDate":"2003-05-19T00:00:00.000+00:00","residentType":"TENANT"}`


### Testing Deletion

Now let's test the deletion of a resident from the original database and see how this change is reflected in the databases of the other microservices.

Resident deletion endpoint for <b>MSResident</b>:

    curl -X DELETE http://localhost:8081/resident/delete/putYourIdHere \
        -H "Content-Type: application/json"

This request will not return any response body. Let's check if the original database actually no longer contains the previously existing resident.

Use the general resident search endpoint for <b>MSResident</b>:

    curl -X GET http://localhost:8081/resident/findAll \
        -H "Content-Type: application/json"

The response from this endpoint should be an empty list: `[]`

Now let's verify if the deletion has been propagated to the databases of the other microservices.

Use the general resident search endpoint for <b>MSCondominium</b>:

    curl -X GET http://localhost:8080/resident/findAll \
       -H "Content-Type: application/json"

The response from this endpoint should be an empty list: `[]`

Use the general resident search endpoint for <b>MSReservation</b>:

    curl -X GET http://localhost:8082/resident/findAll \
       -H "Content-Type: application/json"

The response from this endpoint should be an empty list: `[]`

Notice that in all cases, the deletion was successfully propagated at the database level by our approach.

## Rentable Area Example

To initially confirm the replication process, let's verify that all databases are empty and that no rentable areas have been registered. First, with all containers managed by Docker Compose running, let's check that the original database of the condominium microservice is empty by using a general search endpoint.

General rentable area search endpoint for <b>MSCondominium</b>:

    curl -X GET http://localhost:8080/rentableArea/findAll \
       -H "Content-Type: application/json"

The response to this request should be an empty JSON list, like: `[]`

The rentable areas persisted in the condominium database (Postgres) are transmitted to the reservation database (MariaDB). Let's confirm that this database is also empty by performing another general search for the reservation microservice.

General rentable area search endpoint for <b>MSReservation</b>:

    curl -X GET http://localhost:8082/rentableArea/findAll \
       -H "Content-Type: application/json"

The response to this request should also be an empty list.

### Testing Insertion

After confirming that these databases are empty, let's add a rentable area so we can observe the replication of this data to the other database. To insert a rentable area, we first need to insert a condominium instance.

Condominium insertion endpoint for <b>MSCondominium</b>:

    curl -X POST http://localhost:8080/condominium/save \
        -H "Content-Type: application/json" \
        -d '{
            "name": "Cond",
	        "condominiumType": "HOUSE"
        }'

The response from the request will be the created entity in the database, something like:

`{"id":1,"name":"Cond","condominiumType":"HOUSE"}`

Now, we will add a rentable area associated with this condominium to verify the replication behavior to the other microservice's database.

Rentable area creation endpoint for <b>MSCondominium</b>:

    curl -X POST http://localhost:8080/rentableArea/save \
        -H "Content-Type: application/json" \
        -d '{  
            "name": "Outro salao",
            "size": 50,
            "condominium":{
                "id":1
            },
            "location": "aqui",
            "value": 40,
            "capacity": 6
        }'

The response should look like:

`{"id":1,"name":"Outro salao","size":50,"condominium":{"id":1,"name":null,"condominiumType":null},"location":"aqui","value":40.0,"capacity":6}`

The response from this endpoint should be the rentable area saved in the database. Notice that the response already contains the <i>id</i> field, which is automatically generated by Spring when persisting the entity. To confirm that the rentable area was saved, let's perform a search in the <b>MSCondominium</b> database:

Specific rentable area search endpoint for <b>MSCondominium</b>:

    curl -X GET http://localhost:8080/rentableArea/findById/putYourIdHere \
        -H "Content-Type: application/json"

Or use the general search:

    curl -X GET http://localhost:8080/rentableArea/findAll \
       -H "Content-Type: application/json"

The response should look like:

`{"id":1,"name":"Outro salao","size":50,"condominium":{"id":1,"name":"Cond","condominiumType":"HOUSE"},"location":"aqui","value":40.0,"capacity":6}`

Notice that the response now successfully associates the condominium object with the rentable area simply by its id — a manipulation handled by Spring. Now, let's check if the rentable area has been replicated to the reservation microservice:

Specific rentable area search endpoint for <b>MSReservation</b>:

    curl -X GET http://localhost:8082/rentableArea/findById/putYourIdHere \
       -H "Content-Type: application/json"

Or use the general search:

    curl -X GET http://localhost:8082/rentableArea/findAll \
       -H "Content-Type: application/json"

The response should look like:

`{"id":1,"name":"Outro salao","size":50,"capacity":6,"value":40.0}`

Notice that not only was the replication successful, but also that the replicated rentable area does not contain all the original attributes — only the attributes relevant to the reservation microservice.

### Testing Update

Now lets try update a Rentable Area in the original database and observe how this affect anothers database with Rentable Areas.

Rentable Area update endpoint for <b>MSCondominium</b>:

    curl -X PUT http://localhost:8080/rentableArea/update \
        -H "Content-Type: application/json" \
        -d '{
            "id":"putYourIdHere",
            "name": "Saloon",
            "value": 50
        }'

The response is the updated entity, should look like:

`{"id":2,"name":"Saloon","size":30,"condominium":{"id":1,"name":"Cond","condominiumType":"HOUSE"},"location":"aqui","value":50.0,"capacity":6}`

Now, we can try search this entity in the database of the Reservation microservice.

Use the specific search

    curl -X GET http://localhost:8082/rentableArea/findById/putYourIdHere \
       -H "Content-Type: application/json"

Or use the general search:

    curl -X GET http://localhost:8082/rentableArea/findAll \
       -H "Content-Type: application/json"

The response should look like:

`{"id":2,"name":"Salao","size":30,"capacity":6,"value":50.0}`

### Testing Deletion

Let's test how the deletion of a rentable area in the original database (MSCondominium) can be propagated to the reservation microservice (MSReservation). We will delete the rentable area from the original database.

Rentable area deletion endpoint for <b>MSCondominium</b>:

    curl -X DELETE http://localhost:8080/rentableArea/delete/putYourIdHere \
        -H "Content-Type: application/json"

This request does not have a response body, so let's verify whether our rentable area was actually deleted from the source database.

Use the general search:

    curl -X GET http://localhost:8080/rentableArea/findAll \
        -H "Content-Type: application/json"

The response should be an empty JSON list: `[]`

Now, let's verify if the deletion was replicated to the reservation microservice.

Use the general search:

    curl -X GET http://localhost:8082/rentableArea/findAll \
       -H "Content-Type: application/json"

The response should also be an empty JSON list, thus confirming the replication of the rentable area deletion from the condominium microservice database to the reservation microservice database.
