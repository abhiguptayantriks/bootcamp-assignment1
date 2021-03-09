package com.bootcamp.utils;

import com.bootcamp.model.Employee;
import lombok.experimental.UtilityClass;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;

import java.util.Collections;
import java.util.Objects;
import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

@UtilityClass
public class KafkaGroupUtils {

    private final static String BOOTSTRAP_SERVERS =
            "localhost:9092,localhost:9093,localhost:9094";


    public static Consumer<Long, Employee> createEmployeeConsumer(String topic) {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                "KafkaExampleConsumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "com.bootcamp.serializedeserialize.EmployeeDeserializer");

        // Create the consumer using props.
        final Consumer<Long, Employee> consumer =
                new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);

        // Subscribe to the topic.
        consumer.subscribe(Collections.singletonList(topic));
        return consumer;
    }

    public static Producer<Long, Employee> createEmployeeProducer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                BOOTSTRAP_SERVERS);
        props.put(KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "com.bootcamp.serializedeserialize.EmployeeSerializer");
        Producer<Long, Employee> producer = new org.apache.kafka.clients.producer.KafkaProducer<Long, Employee>(props);
        return producer;
    }

    public static boolean validationOfData(Employee employee){
       return Objects.nonNull(employee.getOrganization()) && Objects.nonNull(employee.getEmpName())
               && Objects.nonNull(employee.getRole()) && Objects.nonNull(employee.getEmpId()) &&
               !employee.getOrganization().isEmpty() && !employee.getEmpName().isEmpty()
               && !employee.getRole().isEmpty() && Objects.nonNull(employee.getEmpId())
               && employee.getEmpName() instanceof String && employee.getOrganization() instanceof String
               && employee.getRole() instanceof String && employee.getEmpId() instanceof Long
               ? true : false;

    }

}
