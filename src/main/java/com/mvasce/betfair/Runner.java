package com.mvasce.betfair;


import com.betfair.esa.client.Client;
import com.betfair.esa.client.auth.AppKeyAndSessionProvider;
import com.betfair.esa.client.auth.InvalidCredentialException;
import com.betfair.esa.client.protocol.ConnectionException;
import com.betfair.esa.client.protocol.StatusException;
import com.betfair.esa.swagger.model.MarketDataFilter;
import com.betfair.esa.swagger.model.MarketFilter;
import com.betfair.esa.swagger.model.MarketSubscriptionMessage;
import com.betfair.esa.swagger.model.MarketDataFilter.FieldsEnum;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Runner {

    public static void main(String[] args) throws StatusException, ConnectionException, InvalidCredentialException {
        AppKeyAndSessionProvider sessionProvider = new AppKeyAndSessionProvider("identitysso.betfair.com", "EfcnNLv9DoymnCjn", "michele.vascellari@gmail.com", "Durlindana66?");
        Client client = new Client("stream-api.betfair.com", 443, sessionProvider);
        KafkaProducer<String, String> producer = KafkaProducerFactory.getProducer();
        client.setChangeHandler(new HandleMarketChanges(producer, "market-changes"));
        MarketSubscriptionMessage subscription = new MarketSubscriptionMessage();
        subscription.setMarketFilter(getMarketFilter());
        subscription.setConflateMs(0L);
        subscription.setHeartbeatMs(1000L);
        subscription.setMarketDataFilter(getMarketDataFilter());
        client.start();
        client.marketSubscription(subscription);
    }

    @NotNull
    private static MarketFilter getMarketFilter() {
        MarketFilter filter = new MarketFilter();
        filter.setMarketTypes(List.of("MATCH_ODDS"));
        filter.setEventTypeIds(List.of("4"));
        filter.setCountryCodes(List.of("GB"));
        return filter;
    }

    @NotNull
    private static MarketDataFilter getMarketDataFilter() {
        MarketDataFilter filter = new MarketDataFilter();
        filter.setLadderLevels(10);
        filter.setFields(List.of(FieldsEnum.EX_BEST_OFFERS_DISP, FieldsEnum.EX_MARKET_DEF, FieldsEnum.EX_TRADED));
        return filter;
    }
}
