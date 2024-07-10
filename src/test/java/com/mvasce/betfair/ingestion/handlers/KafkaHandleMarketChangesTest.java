package com.mvasce.betfair.ingestion.handlers;

import com.betfair.esa.client.Client;
import com.betfair.esa.client.protocol.ChangeMessage;
import com.betfair.esa.client.protocol.ChangeType;
import com.betfair.esa.swagger.model.MarketChange;
import com.betfair.esa.swagger.model.OrderMarketChange;
import com.mvasce.betfair.ingestion.state.BetfairState;
import com.mvasce.betfair.ingestion.state.StateManagerInterface;
import com.mvasce.betfair.models.MarketChangeKey;
import com.mvasce.betfair.models.MarketChangeMessage;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Autowired
    private ConsumerFactory<String, BetfairState> consumerFactory;

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

// /
        ConsumerFactory<MarketChangeKey, MarketChangeMessage> cf = new DefaultKafkaConsumerFactory<>(properties);
        final Consumer<MarketChangeKey, MarketChangeMessage> consumer = cf.createConsumer();

        this.embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, "market-changes");
        final ConsumerRecords<MarketChangeKey, MarketChangeMessage> records = KafkaTestUtils.getRecords(consumer);

        assertEquals(records.count(), 1);
        final ConsumerRecord<MarketChangeKey, MarketChangeMessage> record = records.records("market-changes").iterator().next();
        assertEquals(
                record.key(),
                new MarketChangeKey("1.234556")
        );
        Mockito.verify(stateManager, Mockito.times(1)).setState("AAAAAAA", "BBBBBB");
//        Mockito.verify(stateManager).setState(Mockito.argThat((x, y) -> "AAAAAAA".equals(x) && "BBBBBB".equals(y)));
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