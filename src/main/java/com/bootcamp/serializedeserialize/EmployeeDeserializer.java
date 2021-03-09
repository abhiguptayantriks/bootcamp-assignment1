package com.bootcamp.serializedeserialize;

import com.bootcamp.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmployeeDeserializer implements Deserializer<Employee> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public Employee deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        Employee employee = null;
        try {
            employee = mapper.readValue(bytes, Employee.class);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public void close() {

    }
}
