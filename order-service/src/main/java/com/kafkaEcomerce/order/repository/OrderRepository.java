package com.kafkaEcomerce.order.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kafkaEcomerce.order.entity.OrderEntity;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long>{

	
	
	
}
