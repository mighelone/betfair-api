package com.mvasce.betfair.models;

import com.betfair.esa.client.protocol.ChangeType;
import com.betfair.esa.client.protocol.SegmentType;

public record MarketChangeMessage(
        com.betfair.esa.swagger.model.MarketChange marketChange,
        long arrivalTime,
        long publishTime,
        String clk,
        String initialClk,
        Long heartbeatMs,
        Long conflateMs,
        SegmentType segmentType,
        ChangeType changeType
) {
}
