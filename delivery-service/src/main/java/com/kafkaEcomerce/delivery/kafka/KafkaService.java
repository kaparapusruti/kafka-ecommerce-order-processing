package com.kafkaEcomerce.delivery.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.kafkaEcomerce.delivery.event.DeliveryCreatedEvent;

@Service
public class KafkaService {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public void sendDeliveryCreated(DeliveryCreatedEvent event) {

		kafkaTemplate.send("delivery-created", event);

		System.out.println("DELIVERY_CREATED sent to Kafka");
	}
}