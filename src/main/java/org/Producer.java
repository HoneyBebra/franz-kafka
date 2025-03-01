package org;

import io.github.cdimascio.dotenv.Dotenv;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class Producer {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        String kafka0Host = dotenv.get("KAFKA_0_HOST");
        String kafka1Host = dotenv.get("KAFKA_1_HOST");
        String kafka2Host = dotenv.get("KAFKA_2_HOST");
        String kafka0Port = dotenv.get("KAFKA_0_PORT");
        String kafka1Port = dotenv.get("KAFKA_1_PORT");
        String kafka2Port = dotenv.get("KAFKA_2_PORT");

        Properties kafkaProps = new Properties();
        kafkaProps.put(
                "bootstrap.servers",
                String.format(
                        "%s:%s,%s:%s,%s:%s",
                        kafka0Host,
                        kafka0Port,
                        kafka1Host,
                        kafka1Port,
                        kafka2Host,
                        kafka2Port
                )
        );
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaProps);

        ProducerRecord<String, String> record = new ProducerRecord<>("test", "Hello, World!");

        try {
            producer.send(record).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        producer.close();
    }
}
