package com.kafkaEcomerce.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kafkaEcomerce.payment.entity.PaymentEntity;
import com.kafkaEcomerce.payment.event.OrderCreatedEvent;
import com.kafkaEcomerce.payment.event.PaymentResultEvent;
import com.kafkaEcomerce.payment.kafka.KafkaService;
import com.kafkaEcomerce.payment.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	KafkaService kafkaService;

	public void savePayment(OrderCreatedEvent event) {

		// create payment entity
		PaymentEntity paymentEntity = new PaymentEntity();

		paymentEntity.setOrderId(event.getOrderId());
		paymentEntity.setCustomerId(event.getCustomerId());
		paymentEntity.setAmount(event.getAmount());
		paymentEntity.setPaymentId("TXN-" + event.getOrderId());


		// create payment result event
		PaymentResultEvent result = new PaymentResultEvent();

		result.setEventId("PAY-" + event.getOrderId());
		result.setOrderId(event.getOrderId());
		result.setCustomerId(event.getCustomerId());
		result.setAmount(event.getAmount());
		result.setPaymentId("TXN-" + event.getOrderId());
		result.setDeliveryAddress(event.getDeliveryAddress());
		


		// check payment
		boolean paymentSuccess = event.getAmount() <= 50000;


		if (paymentSuccess) {

			paymentEntity.setPaymentStatus("SUCCESS");

			result.setEventType("PAYMENT_SUCCESS");
			result.setPaymentStatus("SUCCESS");

		} else {

			paymentEntity.setPaymentStatus("FAILED");

			result.setEventType("PAYMENT_FAILED");
			result.setPaymentStatus("FAILED");
			result.setReason("INSUFFICIENT_FUNDS");
		}


		// save in database
		paymentRepository.save(paymentEntity);

		System.out.println("Payment saved in DB");


		// send result to Kafka
		if (paymentSuccess) {

			kafkaService.paymentDetailsSuccess(result);

		} else {

			kafkaService.paymentDetailsfailed(result);
		}
	}
}