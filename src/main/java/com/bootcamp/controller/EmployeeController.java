package com.bootcamp.controller;

import com.bootcamp.kafkabackend.KafkaProducer;
import com.bootcamp.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ExecutionException;

/**
 *REST Controller for publishing employee information
 */
@RestController
public class EmployeeController {

    @Autowired
    KafkaProducer kafkaProducer;

    @PostMapping(value = "/pushToQueue")
    public void sendMessageToKafka(@RequestBody Employee empBody) throws ExecutionException, InterruptedException {
        kafkaProducer.publishToKafka(empBody);
    }

}