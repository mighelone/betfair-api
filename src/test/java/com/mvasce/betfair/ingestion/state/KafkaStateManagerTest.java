package com.mvasce.betfair.ingestion.state;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import com.betfair.esa.client.Client;
import com.mvasce.betfair.models.MarketChangeKey;
import com.mvasce.betfair.models.MarketChangeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {"market-state"})
class KafkaStateManagerTest {
    private final String clk = "AAAAAAAA";
    private final String initialClk = "BBBBBBB";

    @Autowired
    private KafkaStateManager stateManager;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @MockBean
    private Client client;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setState() {
        stateManager.setState(clk, initialClk);

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("stateMgrTest", "true", this.embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        ConsumerFactory<MarketChangeKey, MarketChangeMessage> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        final Consumer<MarketChangeKey, MarketChangeMessage> consumer = cf.createConsumer();

        this.embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, "market-state");
        final ConsumerRecords<MarketChangeKey, MarketChangeMessage> records = KafkaTestUtils.getRecords(consumer);

        assertEquals(records.count(), 1);
        ConsumerRecord<MarketChangeKey, MarketChangeMessage> stateRecord = records.records("market-state").iterator().next();
        assertEquals(stateRecord.value(), new BetfairState(clk, initialClk));
    }

    @Test
    void getState() throws InterruptedException {
        // add some states
        stateManager.setState("A", "B");
        stateManager.setState("B", "B");
        stateManager.setState(clk, initialClk);
        // get the last state
        Thread.sleep(200);
        BetfairState state = stateManager.getState();
        assertEquals(state, new BetfairState(clk, initialClk));
    }
}