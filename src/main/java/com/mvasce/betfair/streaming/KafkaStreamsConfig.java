package com.mvasce.betfair.streaming;

import com.betfair.esa.swagger.model.MarketDefinition;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
        final KStream<MarketChangeKey, MarketChangeMessage> marketChangesStream = kStreamBuilder.stream(inputTopic);
        final KStream<OrderbookKey, RunnerChange> runnerChangesStream = marketChangesStream.flatMap(
                (key, value) -> {
                    OrderbookMetadata metadata = new OrderbookMetadata(value.arrivalTime(), value.publishTime(), value.changeType(), value.segmentType());
                    com.betfair.esa.swagger.model.MarketChange mc = value.marketChange();
                    String marketId = mc.getId();

                    final MarketDefinition marketDefinition = mc.getMarketDefinition();
                    MarketMetadata marketMetadata = new MarketMetadata(
                            mc.isImg(),
                            mc.getTv(),
                            mc.getMarketDefinition()
                    );
                    final List<com.betfair.esa.swagger.model.RunnerChange> rcs = mc.getRc();
                    if (rcs == null) {
                        return new ArrayList<>();
                    }
                    return rcs.stream().map(
                            rc -> new KeyValue<>(
                                    new OrderbookKey(marketId, rc.getId(), rc.getHc()),
                                    new RunnerChange(
                                            mc.getId(),
                                            rc,
                                            metadata,
                                            marketMetadata
                                    )
                            )
                    ).toList();
                }
        );
        final KTable<OrderbookKey, Orderbook> orderbookTable = runnerChangesStream
                .groupByKey()
                .aggregate(
                    Orderbook::empty,
                    (key, value, aggregate) -> {
                        final Optional<MarketMetadata> marketMetadata = Optional.of(value.marketMetadata());
                        if (marketMetadata
                                .map(MarketMetadata::marketDefinition)
                                .map(MarketDefinition::getStatus)
                                .orElse(MarketDefinition.StatusEnum.OPEN)
                                .equals(MarketDefinition.StatusEnum.CLOSED)) {
                            // market is closed release a tombstone
                            log.warn("Closing runner {}", key);
                            return null;
                        }
//                        final Boolean b = Optional.ofNullable(value.marketMetadata()).map(MarketMetadata::img).orElse(false);
//
//                        boolean img = value.marketMetadata().img() != null ? value.marketMetadata().img() : false;
                        if ( marketMetadata.map(MarketMetadata::img).orElse(false) ) {
                            log.warn("Recreate orderbook for key = {}", key);
                            aggregate = Orderbook.empty();
                        }
                        return aggregate.update(value.rc().getBdatb(), value.rc().getBdatl(), value.metadata());
                    },
                        Materialized.as("orderbook")
            );

        final KStream<OrderbookKey, Orderbook> orderbookStream = orderbookTable.toStream();
        orderbookStream.to(outputTopic);
        return orderbookStream;
    }
}
