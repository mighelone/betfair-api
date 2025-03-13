package com.mvasce.betfair.models;

import lombok.NonNull;

public record RunnerChange(
        String marketId,
        com.betfair.esa.swagger.model.RunnerChange rc,
        OrderbookMetadata metadata,
        @NonNull MarketMetadata marketMetadata
) {
}
