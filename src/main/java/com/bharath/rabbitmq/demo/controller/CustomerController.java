package com.bharath.rabbitmq.demo.controller;

import com.bharath.rabbitmq.demo.dto.Customer;
import com.bharath.rabbitmq.demo.dto.Receiver;
import com.bharath.rabbitmq.demo.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/consumer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @PostMapping("/create")
    public Receiver createCustomer(@RequestBody Receiver customer){
        customer.setCustomerId(UUID.randomUUID().toString());
        customerService.create(customer);
        return customer;
    }

    @PutMapping("/addMoney")
    public Receiver addMoney(@RequestBody Receiver customer){
        return customerService.addMoney(customer);
    }

    //test method for now
    @PostMapping("/get")
    public String getOrder() {
//        order.setOrderId(UUID.randomUUID().toString());
//        //restaurantservice
//        //payment service
//        OrderStatus orderStatus = new OrderStatus(order, "PROCESS", "order placed succesfully in " + restaurantName);
//        template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, orderStatus);
        return "get Success !!";
    }
}
