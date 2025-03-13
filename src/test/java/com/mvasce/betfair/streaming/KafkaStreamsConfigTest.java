package com.mvasce.betfair.streaming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.streams.Topology;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.ConsumerFactory;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.betfair.esa.client.protocol.ChangeType;
import com.betfair.esa.swagger.model.MarketChange;
import com.betfair.esa.swagger.model.RunnerChange;
import com.mvasce.betfair.ingestion.state.BetfairState;
import com.mvasce.betfair.models.MarketChangeKey;
import com.mvasce.betfair.models.MarketChangeMessage;
import com.mvasce.betfair.models.Orderbook;
import com.mvasce.betfair.models.OrderbookKey;

/*
 * https://github.com/spring-projects/spring-kafka/blob/main/spring-kafka/src/test/java/org/springframework/kafka/streams/KafkaStreamsTests.java
 */

@SpringBootTest
// @SpringJUnitConfig
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = { "market-changes", "market-state", "orderbook" })
@TestPropertySource(properties = {
    "betfair.topics.market-changes=market-changes",
    "betfair.topics.orderbook=orderbook"
})
public class KafkaStreamsConfigTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    @Autowired
    private KafkaStreamsConfig streamsConfig;

    @Autowired
    private KafkaTemplate<MarketChangeKey, MarketChangeMessage> kafkaTemplate;

    String inputTopic = "market-changes";

    @Test
    void testKStream() {
        this.streamsBuilderFactoryBean.stop();
        Topology topology = this.streamsBuilderFactoryBean.getTopology();
        topology.describe();
        this.streamsBuilderFactoryBean.start();
        kafkaTemplate.send(
            inputTopic, 
            new MarketChangeKey("1.20000"), 
            new MarketChangeMessage(new MarketChange() {{
                setId("1.20000");
                setTv(50.0);
                addRcItem(
                    new RunnerChange() {{ 
                        setId(20000L);
                        addBatbItem(List.of(0.0, 2.3, 50.0));
                        addBdatlItem(List.of(0.0, 2.4, 20.0));
                    }}
                );
            }}, 
            1000L, 1000L, "AAAAA", "BBBBB", 1000L, 0L, null, ChangeType.SUB_IMAGE));
        
        // KafkaStreams kafkaStreams = this.streamsBuilderFactoryBean.getKafkaStreams();
        this.kafkaTemplate.flush();
        assertTrue(this.streamsBuilderFactoryBean.isRunning());

        this.streamsBuilderFactoryBean.stop();

        final Map<String, Object> properties = KafkaTestUtils.consumerProps("testGroup", "true", this.embeddedKafkaBroker);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        

        ConsumerFactory<OrderbookKey,Orderbook> cf = new DefaultKafkaConsumerFactory<>(properties);
        final Consumer<OrderbookKey,Orderbook> consumer = cf.createConsumer();
        this.embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, "orderbook");
        ConsumerRecords<OrderbookKey, Orderbook> records = KafkaTestUtils.getRecords(consumer, Duration.ofSeconds(1));
        assertEquals(records.count(), 1);
    }

    
}
