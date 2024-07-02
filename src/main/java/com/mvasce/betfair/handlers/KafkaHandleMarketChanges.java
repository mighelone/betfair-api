package com.mvasce.betfair.handlers;

import com.betfair.esa.client.protocol.ChangeMessage;
import com.betfair.esa.client.protocol.ChangeMessageHandler;
import com.betfair.esa.swagger.model.MarketChange;
import com.betfair.esa.swagger.model.OrderMarketChange;
import com.betfair.esa.swagger.model.StatusMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaHandleMarketChanges implements ChangeMessageHandler {

    @Autowired
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper mapper = getObjectMapper();

    private ObjectMapper getObjectMapper() {
        ObjectMapper mppr = new ObjectMapper();
        mppr.registerModule(new JavaTimeModule());
        return mppr;
    }
//    private final String topic;

//    public KafkaHandleMarketChanges(KafkaProducer<String,String> producer, String topic) {
//        this.producer = producer;
//        this.topic = topic;
//        mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//    }


    @Override
    public void onOrderChange(ChangeMessage<OrderMarketChange> change) {
        log.info("Order change");
    }

    @Override
    public void onMarketChange(ChangeMessage<MarketChange> change) {
        log.info("Market Change" + change.getChangeType());
        change.getItems().forEach(
                x -> {

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
                        kafkaTemplate.send(
                                "market-changes",
                                key,
                                value
                        );
                        if (log.isDebugEnabled()) log.debug("Market Id=" + x.getId());
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
