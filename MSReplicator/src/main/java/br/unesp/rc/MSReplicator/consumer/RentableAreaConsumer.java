package br.unesp.rc.MSReplicator.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.unesp.rc.CondominiumModel.model.RentableArea;
import br.unesp.rc.CondominiumModel.service.RentableAreaService;
import br.unesp.rc.MSReplicator.domain.mapper.RentableAreaMapper;
import br.unesp.rc.MSReplicator.domain.model.DebeziumMessage;
import br.unesp.rc.MSReplicator.domain.model.DebeziumPayload;
import br.unesp.rc.ReservationModel.service.ReservationRentableAreaService;

@Component
public class RentableAreaConsumer {
    private static final String topic = "MSCondominium.public.rentable_area";
    private static final String group = "case-study-group";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    RentableAreaService rentableAreaCondominiumService;
    
    @Autowired
    ReservationRentableAreaService rentableAreaReservationService;

    @KafkaListener(topics = topic, groupId = group)
    public void consumer(ConsumerRecord<String, String> record) {
        try {
            String messageValue = record.value(); // JSON bruto da mensagem
            
            // Desserializa para o tipo correto
            DebeziumMessage<RentableArea> debeziumMessage = objectMapper.readValue(
                messageValue, new TypeReference<DebeziumMessage<RentableArea>>() {}
            );
            DebeziumPayload<RentableArea> payload = debeziumMessage.getPayload();
            switch (payload.getOperation()) {
                case CREATE:
                    RentableArea rentableAreaPartial = payload.getAfter();
                    RentableArea rentableArea = rentableAreaCondominiumService.findById(rentableAreaPartial.getId());

                    br.unesp.rc.ReservationModel.model.RentableArea reservationRentableArea = RentableAreaMapper.toRentableArea(rentableArea);
                    rentableAreaReservationService.save(reservationRentableArea);

                    
                    System.out.println("Entidade criada: " + reservationRentableArea);
                    break;
                
                case DELETE:
                    rentableAreaReservationService.delete(payload.getBefore().getId());
                    System.out.println("Entidade deletada: " + payload.getBefore());
                    break;
            
                case UPDATE:

                    RentableArea condominiumRentableArea = rentableAreaCondominiumService.findById(payload.getAfter().getId());

                    br.unesp.rc.ReservationModel.model.RentableArea reservationRentableAreaUpdate = RentableAreaMapper.toRentableArea(condominiumRentableArea);

                    rentableAreaReservationService.update(reservationRentableAreaUpdate);
                    System.out.println("Entidade atualizada: " + reservationRentableAreaUpdate);
                    break;
                default:

                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
