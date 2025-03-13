package com.mvasce.betfair.models;

public record OrderbookKey(
        String marketId,
        Long runnerId,
        Double handicap
) {
}
