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

import java.util.List;
import java.util.Optional;

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
        final KStream<MarketChangeKey, MarketChangeMessage> marketChanges = kStreamBuilder.stream(inputTopic);
        final KStream<OrderbookKey, RunnerChange> runnerChanges = marketChanges.flatMap(
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
                    if (Optional.ofNullable(mc.getMarketDefinition()).map(MarketDefinition::getStatus).orElse(MarketDefinition.StatusEnum.OPEN).equals(MarketDefinition.StatusEnum.CLOSED)) {
                        log.warn(
                                "Market {} closed -> available runners {}",
                                marketId,
                                Optional.ofNullable(mc.getRc()).map(List::size).orElse(0)
                                );
                    }
                    return mc.getRc().stream().map(
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
        final KTable<OrderbookKey, Orderbook> orderbookTable = runnerChanges
                .groupByKey()
                .aggregate(
                    Orderbook::empty,
                    (key, value, aggregate) -> {
                        boolean img = value.marketMetadata().img() != null ? value.marketMetadata().img() : false;
                        if ( img ) {
                            log.warn("Recreate orderbook for key = {}", key);
                            aggregate = Orderbook.empty();
                        }
                        return aggregate.update(value.rc().getBdatb(), value.rc().getBdatl(), value.metadata());
                    },
                        Materialized.as("orderbook")
            );

        final KStream<OrderbookKey, Orderbook> orderbooks = orderbookTable.toStream();
        orderbooks.to(outputTopic);
        return orderbooks;
    }
}
