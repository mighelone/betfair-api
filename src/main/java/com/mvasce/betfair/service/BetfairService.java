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
//    @Autowired
//    private final KafkaTemplate<String,String> kafkaTemplate;
    @Autowired
    private final KafkaHandleMarketChanges handler;

//    @Autowired



//    public void process(String key) {}
    @EventListener(ApplicationReadyEvent.class)
    void run()  {
        MarketSubscriptionMessage subscription = MarketSubscriptionMessageFactory.getSubscriptionMessage();
        AppKeyAndSessionProvider sessionProvider = new AppKeyAndSessionProvider("identitysso.betfair.com", "EfcnNLv9DoymnCjn", "michele.vascellari@gmail.com", "Durlindana66?");
        Client client = new Client("stream-api.betfair.com", 443, sessionProvider);
        client.setChangeHandler(handler);
        try {
            client.start();
            client.marketSubscription(subscription);
        } catch (InvalidCredentialException | ConnectionException | StatusException e) {
            throw new RuntimeException(e);
        }
    }
////        String topic = "dummy";
////        while (true) {
////            kafkaTemplate.send(topic, "Name", "sfsgfsggs");
////            log.info("Sent message");
////            Thread.sleep(2000L);
////        }
//    }
}
