package com.kafkaEcomerce.order.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafkaEcomerce.order.request.OrderRequest;
import com.kafkaEcomerce.order.response.OrderResponse;
import com.kafkaEcomerce.order.service.OrderService;

@RestController
@RequestMapping("orders")
public class OrderController {

	@Autowired
	OrderService orderService;

	@PostMapping("/create")
	public OrderResponse create(@RequestBody OrderRequest orderRequest) {

		OrderResponse result = orderService.createOrder(orderRequest);
		System.out.println("the controller ... ordercreate");
		return result;

	}

}
