package com.mvasce.betfair.models;

public record OrderbookLevel(
        Double price,
        Double volume
) {
}
