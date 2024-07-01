package com.mvasce;

import com.betfair.esa.client.Client;
import com.betfair.esa.client.auth.AppKeyAndSessionProvider;
import com.betfair.esa.client.auth.InvalidCredentialException;
import com.betfair.esa.client.protocol.ConnectionException;
import com.betfair.esa.client.protocol.StatusException;
import com.betfair.esa.swagger.model.MarketDataFilter;
import com.betfair.esa.swagger.model.MarketFilter;
import com.betfair.esa.swagger.model.MarketSubscriptionMessage;

import java.util.List;

public class Runner {
//    private final static Logger LOG = LoggerFactory.getLogger(Runner::getClass);

    public static void main(String[] args) throws StatusException, ConnectionException, InvalidCredentialException {
//        LOG.info("Start");

        AppKeyAndSessionProvider sessionProvider = new AppKeyAndSessionProvider(
            AppKeyAndSessionProvider.SSO_HOST_COM,
                "EfcnNLv9DoymnCjn",
                "michele.vascellari@gmail.com",
                "Durlindana66?"
        );
        Client client = new Client(
                "stream-api.betfair.com",  //                "stream-api-integration.betfair.com",
                443,
                sessionProvider
        );
        client.setChangeHandler(new MyHandler());
        MarketSubscriptionMessage subscription = new MarketSubscriptionMessage();
        subscription.setMarketFilter(getMarketFilter());
        subscription.setConflateMs(0L);
        subscription.setHeartbeatMs(1000L);
        subscription.setMarketDataFilter(getMarketDataFilter());
        client.start();
        client.marketSubscription(subscription);
    }

    static private MarketFilter getMarketFilter() {
        MarketFilter filter = new MarketFilter();
        filter.setMarketTypes(List.of("MATCH_ODDS"));
        filter.setEventTypeIds(List.of("4"));
        filter.setCountryCodes(List.of("GB"));
        return filter;
    }

    static private MarketDataFilter getMarketDataFilter() {
        MarketDataFilter filter = new MarketDataFilter();
        filter.setLadderLevels(10);
        filter.setFields(
                List.of(
                        MarketDataFilter.FieldsEnum.EX_BEST_OFFERS,
                        MarketDataFilter.FieldsEnum.EX_BEST_OFFERS_DISP,
                        MarketDataFilter.FieldsEnum.EX_MARKET_DEF,
                        MarketDataFilter.FieldsEnum.EX_TRADED
                )
        );
        return filter;
    }
}
