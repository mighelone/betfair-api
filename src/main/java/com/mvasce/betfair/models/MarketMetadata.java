package com.mvasce.betfair.models;

import com.betfair.esa.swagger.model.MarketDefinition;

public record MarketMetadata(
        Boolean img,
        Double tv,
        MarketDefinition marketDefinition
) {
}
