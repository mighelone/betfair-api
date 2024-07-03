package com.mvasce.betfair.service;

import com.betfair.esa.client.Client;
import com.betfair.esa.client.auth.AppKeyAndSessionProvider;
import com.betfair.esa.client.auth.InvalidCredentialException;
import com.betfair.esa.client.protocol.ConnectionException;
import com.betfair.esa.client.protocol.StatusException;
import com.betfair.esa.swagger.model.MarketSubscriptionMessage;
import com.mvasce.betfair.models.MarketSubscriptionMessageFactory;
import com.mvasce.betfair.handlers.KafkaHandleMarketChanges;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BetfairService {

    @Autowired
    private final Client client;

    @EventListener(ApplicationReadyEvent.class)
    void run()  {
        MarketSubscriptionMessage subscription = MarketSubscriptionMessageFactory.getSubscriptionMessage();
        try {
            client.start();
            client.marketSubscription(subscription);
        } catch (InvalidCredentialException | ConnectionException | StatusException e) {
            throw new RuntimeException(e);
        }
    }

}
