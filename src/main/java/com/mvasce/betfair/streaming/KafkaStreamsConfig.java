package com.mvasce.betfair.streaming;

import com.mvasce.betfair.models.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;

@Slf4j
@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaStreamsConfig {

    @Value("${betfair.topics.market-changes}")
    String inputTopic; // = "market-changes";

    @Value("${betfair.topics.orderbook}")
    String outputTopic; // = "orderbook";


    @Bean
    public KStream<OrderbookKey, Orderbook> kStream(StreamsBuilder kStreamBuilder) {
        JsonSerde<MarketChangeKey> keySerde = new JsonSerde<>(MarketChangeKey.class).forKeys().noTypeInfo();
        JsonSerde<MarketChangeMessage> valueSerde = new JsonSerde<>(MarketChangeMessage.class).noTypeInfo();
        final KStream<MarketChangeKey, MarketChangeMessage> marketChanges = kStreamBuilder.stream(inputTopic);
//        final KStream<MarketChangeKey, MarketChangeMessage> marketChanges = kStreamBuilder.stream(inputTopic,
//                Consumed.with(keySerde, valueSerde)
//        );

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
//        JsonSerde<OrderbookKey>
//        Materialized.with()
        final KTable<OrderbookKey, Orderbook> orderbookTable = runnerChanges
                .groupByKey(
//                        Grouped.with(
//                              new JsonSerde<>(OrderbookKey.class).forKeys().noTypeInfo(),
//                              new JsonSerde<>(RunnerChange.class).noTypeInfo()
//                        )
                )
                .aggregate(
                    Orderbook::empty,
                    (key, value, aggregate) -> aggregate.update(value.rc().getBdatb(), value.rc().getBdatl(), value.metadata()),
                        Materialized.as("orderbook")
//                        Materialized.with(
//                                new JsonSerde<>(OrderbookKey.class).forKeys().noTypeInfo(),
//                                new JsonSerde<>(Orderbook.class).noTypeInfo()
//                        )
            );

        final KStream<OrderbookKey, Orderbook> orderbooks = orderbookTable.toStream();
//        orderbooks.print(Printed.toSysOut());
        orderbooks.to(outputTopic);
        return orderbooks;
    }
}
