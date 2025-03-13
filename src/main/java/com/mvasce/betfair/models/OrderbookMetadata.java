package com.mvasce.betfair.models;

import com.betfair.esa.client.protocol.ChangeType;
import com.betfair.esa.client.protocol.SegmentType;

public record OrderbookMetadata(
        long arrivalTime,
        long publishTime,
        ChangeType changeType,
        SegmentType segmentType
) {
}
