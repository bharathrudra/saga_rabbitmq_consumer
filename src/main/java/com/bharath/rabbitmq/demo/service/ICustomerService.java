package com.bharath.rabbitmq.demo.service;


import com.bharath.rabbitmq.demo.dto.Customer;
import com.bharath.rabbitmq.demo.dto.Order;
import com.bharath.rabbitmq.demo.dto.Receiver;

public interface ICustomerService {

    void customerMessageFromQueue(Order order);

    void purchaseOperation(Order order);

    void create(Receiver customer);

    Receiver addMoney(Receiver customer);

    Receiver deductMoney(Double payment, Receiver customer);
}
