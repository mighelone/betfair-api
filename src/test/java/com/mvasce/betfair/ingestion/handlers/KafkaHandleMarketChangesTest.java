package com.mvasce.betfair.ingestion.handlers;

import com.betfair.esa.client.Client;
import com.betfair.esa.client.protocol.ChangeMessage;
import com.betfair.esa.swagger.model.MarketChange;
import com.mvasce.betfair.ingestion.state.BetfairState;
import com.mvasce.betfair.ingestion.state.StateManagerInterface;
import com.mvasce.betfair.models.MarketChangeKey;
import com.mvasce.betfair.models.MarketChangeMessage;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {"market-changes", "market-state"})
class KafkaHandleMarketChangesTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private KafkaHandleMarketChanges handler;

    @MockBean
    private Client client;

    @MockBean
    private StateManagerInterface stateManager;

    @BeforeEach
    void setUp() {
        Mockito.when(stateManager.getState()).thenReturn(null);
    }

    @Test
    void onMarketChange() {
        ChangeMessage<MarketChange> message = getChangeMessage();
        handler.onMarketChange(message);

        final Map<String, Object> properties = KafkaTestUtils.consumerProps("testGroup", "true", this.embeddedKafkaBroker);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        ConsumerFactory<MarketChangeKey, MarketChangeMessage> cf = new DefaultKafkaConsumerFactory<>(properties);
        final Consumer<MarketChangeKey, MarketChangeMessage> consumer = cf.createConsumer();

        this.embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, "market-changes");
        final ConsumerRecords<MarketChangeKey, MarketChangeMessage> records = KafkaTestUtils.getRecords(consumer);

        // verify that only 1 message was produced
        assertEquals(records.count(), 1);
        final ConsumerRecord<MarketChangeKey, MarketChangeMessage> record = records.records("market-changes").iterator().next();
        // verify the key of the message
        assertEquals(
                record.key(),
                new MarketChangeKey("1.234556")
        );
        // verify that the mock stateManager.setState was invoked with the given state
        Mockito.verify(stateManager, Mockito.times(1)).setState("AAAAAAA", "BBBBBB");
    }

    private ChangeMessage<MarketChange> getChangeMessage() {
        ChangeMessage<MarketChange> message = new ChangeMessage<>();
        var mc = new MarketChange();
        mc.setId("1.234556");
        mc.setTv(100.00);
        message.setClk("AAAAAAA");
        message.setInitialClk("BBBBBB");
        List<MarketChange> marketChanges = List.of(
                mc
        );
        message.setItems(marketChanges);
        return message;
    }

}