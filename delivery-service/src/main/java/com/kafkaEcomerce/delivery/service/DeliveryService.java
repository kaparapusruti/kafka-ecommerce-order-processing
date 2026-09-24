package com.kafkaEcomerce.delivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kafkaEcomerce.delivery.entity.DeliveryEntity;
import com.kafkaEcomerce.delivery.event.DeliveryCreatedEvent;
import com.kafkaEcomerce.delivery.event.PaymentSuccessEvent;
import com.kafkaEcomerce.delivery.kafka.KafkaService;
import com.kafkaEcomerce.delivery.repository.DeliveryRepository;

@Service
public class DeliveryService {

	@Autowired
	DeliveryRepository deliveryRepository;

	@Autowired
	KafkaService kafkaService;

	public void createDelivery(PaymentSuccessEvent event) {

		DeliveryEntity delivery = new DeliveryEntity();

		delivery.setOrderId(event.getOrderId());
		delivery.setCustomerId(event.getCustomerId());
		delivery.setDeliveryAddress(event.getDeliveryAddress());

		delivery.setTrackingNumber("TRACK-" + event.getOrderId());

		delivery.setDeliveryStatus("CREATED");

		deliveryRepository.save(delivery);

		System.out.println("Delivery saved in DB");


		DeliveryCreatedEvent result = new DeliveryCreatedEvent();

		result.setEventId("DEL-" + event.getOrderId());
		result.setEventType("DELIVERY_CREATED");
		result.setOrderId(event.getOrderId());
		result.setCustomerId(event.getCustomerId());
		result.setTrackingNumber(delivery.getTrackingNumber());
		result.setDeliveryAddress(event.getDeliveryAddress());
		result.setDeliveryStatus("CREATED");

		kafkaService.sendDeliveryCreated(result);
	}
}