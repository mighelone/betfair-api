package com.mvasce.betfair.handlers;

import com.betfair.esa.client.protocol.ChangeMessage;
import com.betfair.esa.client.protocol.ChangeMessageHandler;
import com.betfair.esa.swagger.model.MarketChange;
import com.betfair.esa.swagger.model.MarketSubscriptionMessage;
import com.betfair.esa.swagger.model.OrderMarketChange;
import com.betfair.esa.swagger.model.StatusMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mvasce.betfair.state.StateManagerInterface;
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
    private final KafkaTemplate<String,com.mvasce.betfair.models.MarketChange> kafkaTemplate;

    @Autowired
    private final StateManagerInterface stateManager;

//    @Autowired
//    private final MarketSubscriptionMessage marketSubscriptionMessage;

    @Override
    public void onOrderChange(ChangeMessage<OrderMarketChange> change) {
        log.info("Order change");
    }

    @Override
    public void onMarketChange(ChangeMessage<MarketChange> change) {
        log.info("Market Change" + change.getChangeType());
        change.getItems().forEach(
                x -> {
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
                    kafkaTemplate.send(
                            "market-changes",
                            x.getId(),
                            market
                    );
                    if (log.isDebugEnabled()) log.debug("Market Id=" + x.getId());
                }
        );
        stateManager.setState(change.getClk(), change.getInitialClk());
    }

    @Override
    public void onErrorStatusNotification(StatusMessage message) {
        log.error(message.toString());
    }


}
