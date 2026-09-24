package com.kafkaEcomerce.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kafkaEcomerce.kafka.event.OrderCreatedEvent;
import com.kafkaEcomerce.kafka.service.KafkaService;
import com.kafkaEcomerce.order.entity.OrderEntity;
import com.kafkaEcomerce.order.repository.OrderRepository;
import com.kafkaEcomerce.order.request.OrderRequest;
import com.kafkaEcomerce.order.response.OrderResponse;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	KafkaService kafkaService;
	
@Transactional
	public OrderResponse createOrder(OrderRequest orderRequest) {

		// get the order from request to entity

		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setCustomerId(orderRequest.getCustomerId());
		orderEntity.setCustomerName(orderRequest.getCustomerName());
		orderEntity.setProductId(orderRequest.getProductId());
		orderEntity.setProductName(orderRequest.getProductName());
		orderEntity.setQuantity(orderRequest.getQuantity());
		orderEntity.setAmount((double) orderRequest.getAmount());
		orderEntity.setDeliveryAddress(orderRequest.getDeliveryAddress());
		orderEntity.setStatus("ORDER_CREATED");

		// save the data in table
		OrderEntity saveOrder = orderRepository.save(orderEntity);

		//kafka get the data from entity db 
		
		OrderCreatedEvent event = new OrderCreatedEvent();
		
		event.setOrderId(saveOrder.getOrderId());
		event.setCustomerId(saveOrder.getCustomerId());
		event.setAmount(saveOrder.getAmount());
		event.setDeliveryAddress(saveOrder.getDeliveryAddress());
		event.setEventId("EVT-"+ saveOrder.getOrderId());
		event.setEventType(saveOrder.getStatus());
		
		//send data to kafka
		
		kafkaService.sendOrder(event);
		
		// RESPONSE

		OrderResponse response = new OrderResponse();

		response.setOderId(saveOrder.getOrderId());
		response.setStatus(saveOrder.getStatus());

		return response;

	}

}
