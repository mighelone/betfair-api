package com.mvasce.betfair.streaming;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvasce.betfair.models.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class StreamBuilder {

    @Value("${betfair.topics.market-changes}")
    String inputTopic; // = "market-changes";

    @Value("${betfair.topics.orderbook}")
    String outputTopic; // = "orderbook";

//    final String materializedOrderbook = "orderbook";

    @Autowired
    public KStream<OrderbookKey, Orderbook> buildKStream(StreamsBuilder builder) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonSerde<MarketChangeKey> keySerde = new JsonSerde<MarketChangeKey>(mapper);
        keySerde.deserializer().addTrustedPackages("*");
        JsonSerde<MarketChangeMessage> valueSerde = new JsonSerde<MarketChangeMessage>(mapper);
        valueSerde.deserializer().addTrustedPackages("*");
        final KStream<MarketChangeKey, MarketChangeMessage> marketChanges = builder.stream(
                inputTopic
                ,
                Consumed.with(keySerde, valueSerde)
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
                Materialized.as("orderbook")
        );
        final KStream<OrderbookKey, Orderbook> orderbooks = orderbookTable.toStream();
        orderbooks.to(outputTopic);
        return orderbooks;
    }
}
