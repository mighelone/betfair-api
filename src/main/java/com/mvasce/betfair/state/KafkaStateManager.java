package com.mvasce.betfair.state;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class KafkaStateManager implements StateManagerInterface{

    @Value(value = "${betfair.state.topic}")
    private final String topic;

    @Autowired
    private final KafkaTemplate<String, BetfairState> kafkaTemplate;
    
    @Autowired
    private final ConsumerFactory<String,BetfairState> consumerFactory;
    

    @Override
    public void setState(String clk, String initialClk) {
        BetfairState state = new BetfairState(clk, initialClk);
        kafkaTemplate.send(topic, state);
    }

    @Override
    public BetfairState getState() {

        try (Consumer<String, BetfairState> consumer = consumerFactory.createConsumer("group")) {
            TopicPartition tp = new TopicPartition(topic, 0);
            final long offset = consumer.endOffsets(Set.of(tp)).get(tp) - 1;
            consumer.seek(tp, offset);
            BetfairState state;
            final ConsumerRecords<String, BetfairState> records = consumer.poll(Duration.ZERO);
            final List<ConsumerRecord<String, BetfairState>> recordList = records.records(tp);
            final ConsumerRecord<String, BetfairState> record = recordList.get(recordList.size() - 1);
            if (record == null) return null;
            return record.value();
        }
    }
}
