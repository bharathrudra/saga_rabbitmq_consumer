package com.bharath.rabbitmq.demo.service;

import com.bharath.rabbitmq.demo.config.MessagingConfig;
import com.bharath.rabbitmq.demo.dto.Customer;
import com.bharath.rabbitmq.demo.dto.Order;
import com.bharath.rabbitmq.demo.dto.OrderStatus;
import com.bharath.rabbitmq.demo.dto.Receiver;
import com.bharath.rabbitmq.demo.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService{
    private Logger  logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    //@Autowired
    //private MongoTemplate mongoTemplate;

    @RabbitListener(queues = MessagingConfig.ORDER_QUEUE)
    @Override
    public void customerMessageFromQueue(Order order) {
        Order receivedOrder = order;
        System.out.println("##### receivedOrder $$$$$"+receivedOrder);
        logger.info("##### receivedOrder $$$$$ {}",receivedOrder);
        //if the order is placed for more then 6 qty - denied the service
        purchaseOperation(receivedOrder);
    }

    @Override
    public void purchaseOperation(Order order) {
        Receiver dbCustomer = customerRepository.findById(order.getCustomerId()).get();
        validateAndInitiatePayment(order, dbCustomer);
        rabbitTemplate.convertAndSend(MessagingConfig.CUSTOMER_QUEUE,order);
    }

    private void validateAndInitiatePayment(Order order, Receiver dbCustomer) {
        Boolean flag = order.getPrice() > dbCustomer.getBalance() ? Boolean.FALSE : Boolean.TRUE;
        System.out.println("**** FLAG ***"+flag);
        if (!flag){
            System.out.println("!!!!!!!! DECLINED !!!!!!!!!!!!!!!!!"+ order);
            //send the message for denial of order
            order.setStatus(OrderStatus.DECLINED);
            order.setMessage("!! INSUFFICIENT BALANCE, PLEASE TOP-UP WALLET BEFORE PURCHASE !!");
        }else{
            System.out.println("!!!!!!!! ACCEPTED !!!!!!!!!!!!!!!!!"+ order);
            deductMoney(order.getPrice(), dbCustomer);
            //send the message - order accepted
            order.setStatus(OrderStatus.ACCEPTED);
            order.setMessage("!! ORDER ACCEPTED !!");
        }
    }

    @Override
    public void create(Receiver customer) {
        customerRepository.save(customer);
    }

    public Receiver addMoney(Receiver customer) {
        Receiver dbCustomer = customerRepository.findById(customer.getCustomerId()).get();
        Double amtToBeAdded = dbCustomer.getBalance()+customer.getBalance();
        dbCustomer.setBalance(amtToBeAdded);
        customerRepository.save(dbCustomer);
        return dbCustomer;
    }

    public Receiver deductMoney(Double payment, Receiver customer) {
        Double amtAfterPurchase = customer.getBalance()-payment;
        customer.setBalance(amtAfterPurchase);
        customerRepository.save(customer);
        return customer;
    }
}
