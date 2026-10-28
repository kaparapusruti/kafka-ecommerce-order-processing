package com.kafkaEcomerce.delivery.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kafkaEcomerce.delivery.event.PaymentSuccessEvent;
import com.kafkaEcomerce.delivery.service.DeliveryService;

@Service
public class PaymentSuccessConsumer {

	@Autowired
	DeliveryService deliveryService;

	@KafkaListener(
			topics = "payment-success",
			groupId = "delivery-service-group"
	)
	public void consume(PaymentSuccessEvent event) {

		System.out.println("Delivery Service received PAYMENT_SUCCESS");

		System.out.println("Order ID: " + event.getOrderId());
		System.out.println("Customer ID: " + event.getCustomerId());
		System.out.println("Payment Status: " + event.getPaymentStatus());
		System.out.println("Delivery Address: " + event.getDeliveryAddress());

		deliveryService.createDelivery(event);
	}
}
