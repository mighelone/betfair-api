package com.mvasce.betfair.ingestion.handlers;

import com.betfair.esa.client.protocol.ChangeMessage;
import com.betfair.esa.client.protocol.ChangeMessageHandler;
import com.betfair.esa.swagger.model.MarketChange;
import com.betfair.esa.swagger.model.OrderMarketChange;
import com.betfair.esa.swagger.model.StatusMessage;
import com.mvasce.betfair.ingestion.state.StateManagerInterface;
import com.mvasce.betfair.models.MarketChangeKey;
import com.mvasce.betfair.models.MarketChangeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaHandleMarketChanges implements ChangeMessageHandler {

    @Autowired
    private final KafkaTemplate<MarketChangeKey, MarketChangeMessage> kafkaTemplate;

    @Autowired
    private final StateManagerInterface stateManager;

    @Value("${betfair.topics.market-changes}")
    private String topic;


    @Override
    public void onOrderChange(ChangeMessage<OrderMarketChange> change) {
        log.info("Order change");
    }

    @Override
    public void onMarketChange(ChangeMessage<MarketChange> change) {
        log.info("Market Change{}", change.getChangeType());
        change.getItems().forEach(
                x -> {
                    MarketChangeMessage market = new MarketChangeMessage(
                            x,
                            change.getArrivalTime(),
                            change.getPublishTime(),
                            change.getClk(),
                            change.getInitialClk(),
                            change.getHeartbeatMs(),
                            change.getConflateMs(),
                            change.getSegmentType(),
                            change.getChangeType()
                    );
                    kafkaTemplate.send(
                            topic,
                            new MarketChangeKey(x.getId()),
                            market
                    );
                    if (log.isDebugEnabled()) log.debug("Market Id={}", x.getId());
                }
        );
        stateManager.setState(change.getClk(), change.getInitialClk());
    }

    @Override
    public void onErrorStatusNotification(StatusMessage message) {
        log.error(message.toString());
    }


}
