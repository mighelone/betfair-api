package com.mvasce.betfair.streaming.orderbook;

import com.mvasce.betfair.models.*;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.List;

@RequiredArgsConstructor
public class StreamBuilder {

    final String inputTopic;

    final String outputTopic;

    final String materializedOrderbook;

    @Bean
    public KStream<OrderbookKey, Orderbook> buildKStream(StreamsBuilder builder) {
        KStream<String, MarketChange> marketChanges = builder.stream(
                inputTopic,
                Consumed.with(Serdes.String(), new JsonSerde<MarketChange>())
        );

        final KStream<OrderbookKey, RunnerChange> runnerChanges = marketChanges.flatMap(
                (key, value) -> {
                    OrderbookMetadata metadata = new OrderbookMetadata(value.arrivalTime(), value.publishTime(), value.changeType(), value.segmentType());
                    com.betfair.esa.swagger.model.MarketChange mc = value.marketChange();
                    String marketId = mc.getId();
                    return mc.getRc().stream().map(
                            rc -> new KeyValue<>(
                                    new OrderbookKey(marketId, rc.getId(), rc.getHc()),
                                    new RunnerChange(mc.getId(), rc, metadata)
                            )

                    ).toList();
                }
        );
        final KTable<OrderbookKey, Orderbook> orderbookTable = runnerChanges.groupByKey().aggregate(
                Orderbook::empty,
                (key, value, aggregate) -> aggregate.update(value.rc().getBdatb(), value.rc().getBdatl(), value.metadata()),
                Materialized.as(materializedOrderbook)
        );
        final KStream<OrderbookKey, Orderbook> orderbooks = orderbookTable.toStream();
        orderbooks.to(outputTopic);
        return orderbooks;
    }
}
