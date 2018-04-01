package com.innovation.journeyplanning.controller;

import com.innovation.journeyplanning.util.Customer;
import com.innovation.journeyplanning.util.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

@RestController
public class RabbitMqController {
    @Autowired
    private Producer producer;
    @Autowired
    private Customer customer;
    @Autowired
    private AlgorithmController algorithmController;

    @GetMapping(value="test_sender")
    public void sender()throws IOException,TimeoutException {
        String message="test";
        producer.main(message);
    }
    @GetMapping(value = "test_receiver")
    public void receiver()throws IOException,java.lang.InterruptedException,TimeoutException,ParseException {
        customer.main();
    }
}
