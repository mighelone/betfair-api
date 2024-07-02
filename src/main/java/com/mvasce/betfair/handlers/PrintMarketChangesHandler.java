package com.mvasce.betfair.handlers;

import com.betfair.esa.client.protocol.ChangeMessage;
import com.betfair.esa.client.protocol.ChangeMessageHandler;
import com.betfair.esa.swagger.model.MarketChange;
import com.betfair.esa.swagger.model.OrderMarketChange;
import com.betfair.esa.swagger.model.StatusMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrintMarketChangesHandler implements ChangeMessageHandler  {
    @Override
    public void onOrderChange(ChangeMessage<OrderMarketChange> change) {
        log.info("Order change");
    }

    @Override
    public void onMarketChange(ChangeMessage<MarketChange> change) {
        log.info("Market Change" + change.getChangeType());
        change.getItems().forEach(
                x -> log.info("Market id = " + x.getId())
        );
    }

    @Override
    public void onErrorStatusNotification(StatusMessage message) {
        log.error(message.toString());
    }
}
