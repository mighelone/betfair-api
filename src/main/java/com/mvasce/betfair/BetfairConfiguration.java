package com.mvasce.betfair;

import com.betfair.esa.client.Client;
import com.betfair.esa.client.auth.AppKeyAndSessionProvider;
import com.mvasce.betfair.handlers.KafkaHandleMarketChanges;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    @Autowired
    private final KafkaHandleMarketChanges handler;

    @Bean
    public Client getClient() {
        log.info("Initializing client: host: " + hostName  );
        AppKeyAndSessionProvider sessionProvider = new AppKeyAndSessionProvider(ssoHost, appKey, username, password);
        Client client = new Client(hostName, port, sessionProvider);
        client.setChangeHandler(handler);
        return client;
    }
}
