package com.mvasce.betfair.models;

public record RunnerChange(
        String marketId,
        com.betfair.esa.swagger.model.RunnerChange rc,
        OrderbookMetadata metadata,
        MarketMetadata marketMetadata
) {
}
