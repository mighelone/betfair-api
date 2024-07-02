package com.mvasce.betfair;


import com.betfair.esa.swagger.model.MarketDataFilter;
import com.betfair.esa.swagger.model.MarketDataFilter.FieldsEnum;
import com.betfair.esa.swagger.model.MarketFilter;

import java.util.List;

public class BetfairStreamingClient {
//    private final Client client;

//    public static void main(String[] args) throws StatusException, ConnectionException, InvalidCredentialException {
//        AppKeyAndSessionProvider sessionProvider = new AppKeyAndSessionProvider("identitysso.betfair.com", "EfcnNLv9DoymnCjn", "michele.vascellari@gmail.com", "Durlindana66?");
//        Client client = new Client("stream-api.betfair.com", 443, sessionProvider);
//        KafkaProducer<String, String> producer = KafkaProducerFactory.getProducer();
//        client.setChangeHandler(new HandleMarketChanges(producer, "market-changes"));
//        MarketSubscriptionMessage subscription = new MarketSubscriptionMessage();
//        subscription.setMarketFilter(getMarketFilter());
//        subscription.setConflateMs(0L);
//        subscription.setHeartbeatMs(1000L);
//        subscription.setMarketDataFilter(getMarketDataFilter());
//        client.start();
//        client.marketSubscription(subscription);
//    }

    private static MarketFilter getMarketFilter() {
        MarketFilter filter = new MarketFilter();
        filter.setMarketTypes(List.of("MATCH_ODDS"));
        filter.setEventTypeIds(List.of("4"));
        filter.setCountryCodes(List.of("GB"));
        return filter;
    }

    private static MarketDataFilter getMarketDataFilter() {
        MarketDataFilter filter = new MarketDataFilter();
        filter.setLadderLevels(10);
        filter.setFields(List.of(FieldsEnum.EX_BEST_OFFERS_DISP, FieldsEnum.EX_MARKET_DEF, FieldsEnum.EX_TRADED));
        return filter;
    }

//    public Client getClient() {
//                AppKeyAndSessionProvider sessionProvider = new AppKeyAndSessionProvider("identitysso.betfair.com", "EfcnNLv9DoymnCjn", "michele.vascellari@gmail.com", "Durlindana66?");
//        Client client = new Client("stream-api.betfair.com", 443, sessionProvider);
//        KafkaProducer<String, String> producer = KafkaProducerFactory.getProducer();
//        client.setChangeHandler(new HandleMarketChanges(producer, "market-changes"));
//        MarketSubscriptionMessage subscription = new MarketSubscriptionMessage();
//        subscription.setMarketFilter(getMarketFilter());
//        subscription.setConflateMs(0L);
//        subscription.setHeartbeatMs(1000L);
//        subscription.setMarketDataFilter(getMarketDataFilter());
//        client.start();
//        client.marketSubscription(subscription);
//    }
}
