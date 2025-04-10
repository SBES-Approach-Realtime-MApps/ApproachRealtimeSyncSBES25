package br.unesp.rc.MSReplicator.consumer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.unesp.rc.CondominiumModel.service.CondominiumResidentService;
import br.unesp.rc.MSReplicator.domain.mapper.ResidentMapper;
import br.unesp.rc.MSReplicator.domain.model.DebeziumMessage;
import br.unesp.rc.MSReplicator.domain.model.DebeziumPayload;
import br.unesp.rc.ReservationModel.service.ReservationResidentService;
import br.unesp.rc.ResidentModel.model.Address;
import br.unesp.rc.ResidentModel.model.Contact;
import br.unesp.rc.ResidentModel.model.Resident;
import br.unesp.rc.ResidentModel.model.ResidentType;

@Component
public class ResidentConsumer {
    private static final String topic = "MSResident.MSResident.resident";
    private static final String group = "case-study-group";


    @Autowired
    @Qualifier("reservationResidentService")
    ReservationResidentService residentService;

    @Autowired
    @Qualifier("condominiumResidentService")
    CondominiumResidentService condominiumResidentService;

    @KafkaListener(topics = topic, groupId = group)
    public void consumer(ConsumerRecord<String, String> record) {
        try {
            String messageValue = record.value(); // JSON da mensagem

            // 1. Desserializa mensagem
            DebeziumMessage<Resident> debeziumMessage = deserializeDebeziumMessage(messageValue);

            // 2. Acessa o "after" (pode ser null em deletes)
            DebeziumPayload<Resident> payload = debeziumMessage.getPayload();
            System.out.println("Operação: " + payload.getOperation());

            switch (payload.getOperation()) {
                case CREATE:
                    Resident resident = payload.getAfter();

                    // Mapeando o objeto para a reserva
                    br.unesp.rc.ReservationModel.model.Resident reservationResident = ResidentMapper.toReservationResident(resident);

                    // Salvando
                    residentService.save(reservationResident);

                    // Mapeando para o condomínio
                    br.unesp.rc.CondominiumModel.model.Resident condominiumResident = ResidentMapper.toCondominiumResident(resident);

                    condominiumResidentService.save(condominiumResident);

                    break;
                
                case DELETE:
                
                    String key = record.key().replace("\\\"", "\"");
                    key = key.replace("\"{", "{").replace("}\"", "}");
                    System.out.println(key);

                    // Expressão regular para pegar o valor do $oid
                    Pattern pattern = Pattern.compile("\\$oid\":\\s*\"([^\"]+)\"");
                    Matcher matcher = pattern.matcher(key);
                    
                    if (matcher.find()) {
                        String extractedId = matcher.group(1);
                        System.out.println("ID extraído: " + extractedId);
                        residentService.delete(extractedId);

                        condominiumResidentService.delete(extractedId);
                    } else {
                        System.out.println("ID não encontrado.");
                    }
                    break;
                
                case UPDATE:
                    Resident updateResident = payload.getAfter();
                    
                    // Mapeando para reservation
                    br.unesp.rc.ReservationModel.model.Resident reservationUpdateResident = ResidentMapper.toReservationResident(updateResident);

                    // Salvando
                    residentService.update(reservationUpdateResident);

                    // Mapeando para condomínio
                    br.unesp.rc.CondominiumModel.model.Resident condominiumUpdateResident = ResidentMapper.toCondominiumResident(updateResident);

                    condominiumResidentService.update(condominiumUpdateResident);
                    break;
                default:
                    break;
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao processar mensagem: ");
            e.printStackTrace();
        }
    }

    public DebeziumMessage<Resident> deserializeDebeziumMessage(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Permite lidar com datas no formato {"$date": ...}
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonNode root = mapper.readTree(json);

        // 1. Lê o campo "schema"
        JsonNode schemaNode = root.path("schema");

        // 2. Lê o campo "payload"
        JsonNode payloadNode = root.path("payload");

        // 3. Lê os campos "before", "after" e "op" da payload
        JsonNode afterNode = payloadNode.path("after");
        String jsonAfterNodeString = afterNode.toString().replace("\\\"", "\"");
        jsonAfterNodeString = jsonAfterNodeString.replace("\"{", "{").replace("}\"", "}");
        afterNode = mapper.readTree(jsonAfterNodeString);

        JsonNode beforeNode = payloadNode.path("before");
        String jsonBeforeNodeString = beforeNode.toString().replace("\\\"", "\"");
        jsonBeforeNodeString = jsonAfterNodeString.replace("\"{", "{").replace("}\"", "");
        beforeNode = mapper.readTree(jsonBeforeNodeString);

        String op = payloadNode.path("op").asText();

        // 4. Constrói o objeto Resident manualmente a partir de afterNode
        Resident after = null;
        if (!afterNode.isMissingNode() && !afterNode.isNull()) {
            after = buildResidentFromJson(afterNode);
        }

        Resident before = null;
        if (!beforeNode.isMissingNode() && !beforeNode.isNull()) {
            before = buildResidentFromJson(beforeNode);
        }

        // 5. Monta o payload e a mensagem final
        DebeziumPayload<Resident> payload = new DebeziumPayload<>(before, after, op);
        return new DebeziumMessage<>(schemaNode, payload);
    }

    private Resident buildResidentFromJson(JsonNode node) {
        Resident resident = new Resident();

        // ID
        JsonNode idNode = node.path("_id").path("$oid");
        resident.setId(idNode.asText());
        

        // Name
        resident.setName(node.path("name").asText());

        // BirthDate
        JsonNode birthDateNode = node.path("birthDate").path("$date");
        if (!birthDateNode.isMissingNode()) {
            resident.setBirthDate(new Date(birthDateNode.asLong()));
        }

        // ResidentType
        resident.setResidentType(ResidentType.valueOf(node.path("residentType").asText()));

        // Contact
        JsonNode contactNode = node.path("contact");
        if (!contactNode.isMissingNode()) {
            Contact contact = new Contact();
            contact.setBusinessPhone(contactNode.path("businessPhone").asText(null));
            contact.setCellPhone(contactNode.path("cellPhone").asText(null));
            contact.setEmail(contactNode.path("email").asText(null));
            resident.setContact(contact);
        }

        // Address
        JsonNode addressArray = node.path("address");
        if (addressArray.isArray()) {
            List<Address> addresses = new ArrayList<>();
            for (JsonNode addrNode : addressArray) {
                Address address = new Address();
                address.setStreet(addrNode.path("street").asText(null));
                address.setNumber(addrNode.path("number").asText(null));
                address.setNeighborhood(addrNode.path("neighborhood").asText(null));
                address.setCity(addrNode.path("city").asText(null));
                address.setState(addrNode.path("state").asText(null));
                address.setZipCode(addrNode.path("zipCode").asText(null));
                addresses.add(address);
            }
            resident.setAddress(addresses);
        }

        return resident;
    }



}
