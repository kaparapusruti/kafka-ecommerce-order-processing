package com.kafkaEcomerce.payment.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kafkaEcomerce.payment.event.OrderCreatedEvent;
import com.kafkaEcomerce.payment.service.PaymentService;

@Service
public class OrderConsumer {
	
	@Autowired
	PaymentService paymentService;
	
	@KafkaListener(topics = "order-created",groupId = "payment-service-group")
	
	public void consume(OrderCreatedEvent  event) {
		
		System.out.println("payment service rec order");
		
		System.out.println("orderId: " + event.getOrderId() );
		System.out.println("customerId: " + event.getCustomerId() );
		System.out.println("deliveryAddress: " + event.getDeliveryAddress() );
		System.out.println("eventId: " + event.getEventId() );
		System.out.println("eventType: " + event.getEventType() );
		System.out.println("amount: " + event.getAmount() );
		
		paymentService.savePayment(event);
		
		
	}
	

}
