package com.mvasce.betfair;

import com.betfair.esa.client.protocol.ChangeMessage;
import com.betfair.esa.client.protocol.ChangeMessageHandler;
import com.betfair.esa.swagger.model.MarketChange;
import com.betfair.esa.swagger.model.OrderMarketChange;
import com.betfair.esa.swagger.model.StatusMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

@Slf4j
public class HandleMarketChanges implements ChangeMessageHandler {
    private final KafkaProducer<String,String> producer;
    private final ObjectMapper mapper;
    private final String topic;

    public HandleMarketChanges(KafkaProducer<String,String> producer, String topic) {
        this.producer = producer;
        this.topic = topic;
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }


    @Override
    public void onOrderChange(ChangeMessage<OrderMarketChange> change) {
        log.info("Order change");
    }

    @Override
    public void onMarketChange(ChangeMessage<MarketChange> change) {
        log.info("Market Change" + change.getChangeType());
        change.getItems().forEach(

                x -> {
                    log.info("Market Id=" + x.getId());
                    String key = x.getId();
                    try {
                        com.mvasce.betfair.models.MarketChange market = new com.mvasce.betfair.models.MarketChange(
                                x,
                                change.getArrivalTime(),
                                change.getPublishTime(),
                                change.getId(),
                                change.getClk(),
                                change.getInitialClk(),
                                change.getHeartbeatMs(),
                                change.getConflateMs(),
                                change.getSegmentType(),
                                change.getChangeType()
                        );
                        String value = mapper.writeValueAsString(market);
                        ProducerRecord<String,String> record = new ProducerRecord<>(
                                topic,
                                key,
                                value);
                        producer.send(record);

                    } catch (JsonProcessingException e) {
//                        throw new RuntimeException(e);
                        log.error("Failed serializing market id=" + x.getId());
                    }
                }


        );
    }

    @Override
    public void onErrorStatusNotification(StatusMessage message) {

    }


}
