package com.bharath.rabbitmq.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@Entity
//@Table(name = "customer")
public class Customer {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String customerId;
//    @Column
    private String name;
//    @Column
    private Double balance;
}
