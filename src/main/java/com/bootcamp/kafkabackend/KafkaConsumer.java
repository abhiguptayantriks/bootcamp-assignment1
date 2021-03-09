package com.bootcamp.kafkabackend;

import com.bootcamp.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.bootcamp.utils.KafkaGroupUtils.*;

//@KafkaListener(topics = "employee")
@Component
@Slf4j
public class KafkaConsumer{
    private final static String TOPIC = "employee";

    /**
     * This Kafka Listener reads the data initially being published to a test topic.
     * Validation of records takes place here and then forwarded to the appropriate queue.
     * @param data
     */

//    @KafkaHandler
    @KafkaListener(topics = {"employee"}, groupId = "KafkaConsumerExample")
    void runConsumer(Object data) {
//        System.out.println("#### -> Consumed message -> " +data.toString());
        final Consumer<Long, Employee> consumerEmployee = createEmployeeConsumer(TOPIC);
        final Producer<Long, Employee> producerCorrectData = createEmployeeProducer();

        final int giveUp = 100;
        int noRecordsCount = 0;

        while (true) {
            final ConsumerRecords<Long, Employee> consumerRecords =
                    consumerEmployee.poll(1000);

            if (consumerRecords.count() == 0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) break;
                else continue;
            }

            consumerRecords.forEach(record -> {
                log.info("Consumer Record: {} {} {} {}",
                        record.key(), record.value(),
                        record.partition(), record.offset());
                if (validationOfData(record.value())) {
                    final ProducerRecord<Long, Employee> correctRecord =
                            new ProducerRecord<>("correctEmployee", record.key(),
                                    record.value());
                    producerCorrectData.send(correctRecord);
                }
                else {
                    final ProducerRecord<Long, Employee> dlqRecord =
                            new ProducerRecord<>("DeadLetterQueue", record.key(),
                                    record.value());
                    producerCorrectData.send(dlqRecord);
                }
            });


            consumerEmployee.commitAsync();
        }
        consumerEmployee.close();
    }

    /**
     * This Kafka Listener is for consuming the records posted in the main topic after validation
     * @param data
     */
    @KafkaListener(topics = {"correctEmployee"}, groupId = "KafkaConsumerExample")
    void runConsumerDLQ(Object data) {
        log.info("#### -> Consumed message in correct topic -> " +data.toString());
    }

    /**
     * This Kafka Listener is for consuming the records being posted in the Dead Letter Queue
     * @param data
     */

    @KafkaListener(topics = {"DeadLetterQueue"}, groupId = "KafkaConsumerExample")
    void runConsumerMain(Object data) {
        log.info("#### -> Consumed message in DLQ -> " +data.toString());
    }



}
