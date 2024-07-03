package com.mvasce.betfair.service;

import com.betfair.esa.client.Client;
import com.betfair.esa.client.auth.InvalidCredentialException;
import com.betfair.esa.client.protocol.ConnectionException;
import com.betfair.esa.client.protocol.StatusException;
import com.betfair.esa.swagger.model.MarketSubscriptionMessage;
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

    @Autowired
    private final MarketSubscriptionMessage marketSubscriptionMessage;

    @EventListener(ApplicationReadyEvent.class)
    void run()  {
        try {
            client.start();
            client.marketSubscription(marketSubscriptionMessage);
        } catch (InvalidCredentialException | ConnectionException | StatusException e) {
            throw new RuntimeException(e);
        }
    }

}
