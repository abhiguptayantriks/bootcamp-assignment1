package com.bootcamp.kafkabackend;

import com.bootcamp.model.Employee;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Service
public class KafkaProducer {

    @Autowired
    KafkaTemplate<Object, ProducerRecord<Long, Employee>> kafkaTemplate;

    /**
     * This method gets information via the REST call and makes a decision to either publish the
     * records in Dead Letter Queue (if it fails validation) or the main target queu
     * @param empBody
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void publishToKafka(Employee empBody) throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        properties.put(VALUE_SERIALIZER_CLASS_CONFIG, "com.bootcamp.serializedeserialize.EmployeeSerializer");

        Producer<Long, Employee> producer = new org.apache.kafka.clients.producer.KafkaProducer<Long, Employee>(properties);

        long time = System.currentTimeMillis();
        try {
//
                final ProducerRecord<Long, Employee> record =
                        new ProducerRecord<>("employee", time,
                                empBody);

                RecordMetadata metadata = producer.send(record).get();
//                kafkaTemplate.send("employee", record);
                long elapsedTime = System.currentTimeMillis() - time;
                System.out.printf("sent record(key=%s value=%s) " +
                                "meta(partition=%d, offset=%d) time=%d\n",
                        record.key(), record.value(), metadata.partition(),
                        metadata.offset(), elapsedTime);
//            System.out.println("sent record(key=" + record.key() + "value=) " +record.value());


        } finally {
            producer.flush();
            producer.close();
        }

    }
}
