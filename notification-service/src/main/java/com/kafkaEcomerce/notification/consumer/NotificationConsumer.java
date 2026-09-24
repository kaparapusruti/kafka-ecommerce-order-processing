package com.kafkaEcomerce.notification.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

	@KafkaListener(
			topics = "order-created",
			groupId = "notification-service-group"
	)
	public void orderCreated(String message) {

		System.out.println("=================================");
		System.out.println("ORDER CREATED NOTIFICATION");
		System.out.println(message);
		System.out.println("=================================");
	}


	@KafkaListener(
			topics = "payment-success",
			groupId = "notification-service-group"
	)
	public void paymentSuccess(String message) {

		System.out.println("=================================");
		System.out.println("PAYMENT SUCCESS NOTIFICATION");
		System.out.println(message);
		System.out.println("=================================");
	}


	@KafkaListener(
			topics = "payment-failed",
			groupId = "notification-service-group"
	)
	public void paymentFailed(String message) {

		System.out.println("=================================");
		System.out.println("PAYMENT FAILED NOTIFICATION");
		System.out.println(message);
		System.out.println("=================================");
	}


	@KafkaListener(
			topics = "delivery-created",
			groupId = "notification-service-group"
	)
	public void deliveryCreated(String message) {

		System.out.println("=================================");
		System.out.println("DELIVERY CREATED NOTIFICATION");
		System.out.println(message);
		System.out.println("=================================");
	}
}