package com.mvasce.betfair.ingestion;

import com.betfair.esa.client.Client;
import com.betfair.esa.client.auth.AppKeyAndSessionProvider;
import com.betfair.esa.swagger.model.MarketDataFilter;
import com.betfair.esa.swagger.model.MarketFilter;
import com.betfair.esa.swagger.model.MarketSubscriptionMessage;
import com.mvasce.betfair.ingestion.handlers.KafkaHandleMarketChanges;
import com.mvasce.betfair.ingestion.state.BetfairState;
import com.mvasce.betfair.ingestion.state.StateManagerInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
@ConfigurationProperties("betfair")
@RequiredArgsConstructor
public class BetfairConfiguration {

    @Value("${betfair.hostName}")
    private String hostName;
    @Value("${betfair.port}")
    private int port;
    @Value("${betfair.ssoHost}")
    private String ssoHost;
    @Value("${betfair.appKey}")
    private String appKey;
    @Value("${betfair.username}")
    private String username;
    @Value("${betfair.password}")
    private String password;
    @Value("${betfair.eventTypeId}")
    private String eventTypeId;
    @Value("${betfair.marketType}")
    private String marketType;

    @Autowired
    private final KafkaHandleMarketChanges handler;
    @Autowired
    private final StateManagerInterface stateManager;


    @Bean
    public Client getClient() {
        log.info("Initializing client: host: {}", hostName);
        AppKeyAndSessionProvider sessionProvider = new AppKeyAndSessionProvider(ssoHost, appKey, username, password);
        Client client = new Client(hostName, port, sessionProvider);
        client.setAutoReconnect(true);
        client.setChangeHandler(handler);
        return client;
    }

    @Bean
    public MarketSubscriptionMessage getMarketSubscriptionMessage() {
        BetfairState state = stateManager.getState();
        MarketSubscriptionMessage subscriptionMessage = new MarketSubscriptionMessage();
        MarketFilter marketFilter = getMarketFilter();
        MarketDataFilter marketDataFilter = getMarketDataFilter();
        subscriptionMessage.setMarketDataFilter(marketDataFilter);
        if (state != null) {
            subscriptionMessage.setClk(state.clk());
            subscriptionMessage.setInitialClk(state.initialClk());
        }
        subscriptionMessage.setMarketFilter(marketFilter);
        subscriptionMessage.setConflateMs(0L);
        subscriptionMessage.setHeartbeatMs(1000L);
        return subscriptionMessage;
    }

    protected MarketFilter getMarketFilter() {
        MarketFilter filter = new MarketFilter();
        filter.setMarketTypes(List.of(marketType));
        filter.setEventTypeIds(List.of(eventTypeId));
        filter.setCountryCodes(List.of("GB"));
        return filter;
    }

    protected MarketDataFilter getMarketDataFilter() {
        MarketDataFilter filter = new MarketDataFilter();
        filter.setLadderLevels(10);
        filter.setFields(List.of(MarketDataFilter.FieldsEnum.EX_BEST_OFFERS_DISP, MarketDataFilter.FieldsEnum.EX_MARKET_DEF, MarketDataFilter.FieldsEnum.EX_TRADED));
        return filter;
    }
}
