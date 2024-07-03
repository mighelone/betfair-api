package com.mvasce.betfair.state;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StateManagerConfiguration {
    @Bean
    public StateManagerInterface getFileStateManager() {
        return new FileStateManager("state.json");
    }
}
