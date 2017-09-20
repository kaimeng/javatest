package com.meng.home.kafka;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class KfkProducer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<>("my-topic", Integer.toString(i), Integer.toString(i)),
                    (metadata, e) -> {
                        if (e != null) {
                            e.printStackTrace();
                        }
                        System.out.println("The offset of the record we just sent is: " + metadata.offset());
                    });
        }
        producer.close();
    }
}
