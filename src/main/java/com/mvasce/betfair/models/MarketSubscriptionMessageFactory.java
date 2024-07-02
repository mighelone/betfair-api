package com.mvasce.betfair.models;

import com.betfair.esa.swagger.model.MarketDataFilter;
import com.betfair.esa.swagger.model.MarketFilter;
import com.betfair.esa.swagger.model.MarketSubscriptionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MarketSubscriptionMessageFactory {
//    Client createClient() {
//        AppKeyAndSessionProvider sessionProvider = new AppKeyAndSessionProvider("identitysso.betfair.com", "EfcnNLv9DoymnCjn", "michele.vascellari@gmail.com", "Durlindana66?");
//        Client client = new Client("stream-api.betfair.com", 443, sessionProvider);
//        client.setChangeHandler(new PrintMarketChangesHandler());
//        return client;
//    }

    protected static MarketFilter getMarketFilter() {
        MarketFilter filter = new MarketFilter();
        filter.setMarketTypes(List.of("MATCH_ODDS"));
        filter.setEventTypeIds(List.of("4"));
        filter.setCountryCodes(List.of("GB"));
        return filter;
    }

    protected static MarketDataFilter getMarketDataFilter() {
        MarketDataFilter filter = new MarketDataFilter();
        filter.setLadderLevels(10);
        filter.setFields(List.of(MarketDataFilter.FieldsEnum.EX_BEST_OFFERS_DISP, MarketDataFilter.FieldsEnum.EX_MARKET_DEF, MarketDataFilter.FieldsEnum.EX_TRADED));
        return filter;
    }

    public static MarketSubscriptionMessage getSubscriptionMessage() {
        MarketSubscriptionMessage subscriptionMessage = new MarketSubscriptionMessage();
        MarketFilter marketFilter = getMarketFilter();
        MarketDataFilter marketDataFilter = getMarketDataFilter();
        subscriptionMessage.setMarketDataFilter(marketDataFilter);
        subscriptionMessage.setMarketFilter(marketFilter);
        subscriptionMessage.setConflateMs(0L);
        subscriptionMessage.setHeartbeatMs(1000L);
        return subscriptionMessage;
    }
}
