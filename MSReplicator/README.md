# MSReplicator
This is the microservice responsible for listening to the Kafka topic related to changes in the databases of the respective entity to be propagated. Within this module, it also deserializes the message sent from Debezium to Kafka into an entity for propagation.

The system workflow and the propagation process are better presented in the paper.

Implementation details are as follows:
- OpenJDK 21
- SpringFramework 3.4.4
- Debezium 2.6.1.Final
- Kafka 7.6.1
- Zookeeper 7.6.1

<b>Note:</b> Kafka, Debezium, and Zookeeper do not run locally within this microservice, but the way this case study is implemented depends on the versions listed above.