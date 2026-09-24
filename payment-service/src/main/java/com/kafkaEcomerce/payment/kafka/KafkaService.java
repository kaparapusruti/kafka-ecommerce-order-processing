package com.kafkaEcomerce.payment.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.kafkaEcomerce.payment.event.PaymentResultEvent;

@Service
public class KafkaService {

	@Autowired
	 KafkaTemplate<String,Object> kafkaTemplate;
	
	public void paymentDetailsSuccess(PaymentResultEvent event) {
		
		kafkaTemplate.send("payment-success", event);
		
	System.out.println("KafkaService.paymentDetailsSuccess()");
		
	}
	
public void paymentDetailsfailed(PaymentResultEvent event) {
		
		kafkaTemplate.send("payment-failed", event);
		
		System.out.println("KafkaService.paymentDetailsfailed()");
		
	}
	
}
