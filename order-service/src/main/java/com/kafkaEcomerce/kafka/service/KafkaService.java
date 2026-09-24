package com.kafkaEcomerce.kafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.kafkaEcomerce.kafka.event.OrderCreatedEvent;

@Service
public class KafkaService {
	
	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;
	
	public void sendOrder(OrderCreatedEvent event) {
		
		kafkaTemplate.send("order-created" , event);
		
		System.out.println("order sent to kafka");
	}
	
	

}
