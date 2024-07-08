package com.mvasce.betfair.state;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaStateManager implements StateManagerInterface{

//    @Value(value = "${betfair.state.topic}")
    private final String topic = "market-state";

    @Autowired
    private final KafkaTemplate<String, BetfairState> kafkaTemplate;
    
    @Autowired
    private final ConsumerFactory<String,BetfairState> consumerFactory;
    

    @Override
    public void setState(String clk, String initialClk) {
        BetfairState state = new BetfairState(clk, initialClk);
        kafkaTemplate.send(topic, "betfair", state);
    }

    @Override
    public BetfairState getState() {

        try (Consumer<String, BetfairState> consumer = consumerFactory.createConsumer("group")) {
            TopicPartition tp = new TopicPartition(topic, 0);
            final long offset = consumer.endOffsets(Set.of(tp)).get(tp) - 1;
            if (offset < 0) {
                log.warn("No offset defined in topic {}", topic);
                return null;
            }
            consumer.assign(Set.of(tp));
            consumer.seek(tp, offset);
            final ConsumerRecords<String, BetfairState> records = consumer.poll(Duration.ofSeconds(1));
            final List<ConsumerRecord<String, BetfairState>> recordList = records.records(tp);
            final ConsumerRecord<String, BetfairState> record = recordList.get(recordList.size() - 1);
            return record != null ? record.value() : null;
        }
    }
}
