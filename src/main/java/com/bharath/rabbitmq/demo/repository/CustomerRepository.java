package com.bharath.rabbitmq.demo.repository;

import com.bharath.rabbitmq.demo.dto.Customer;
import com.bharath.rabbitmq.demo.dto.Receiver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface CustomerRepository extends CrudRepository<Receiver,String>{
}
