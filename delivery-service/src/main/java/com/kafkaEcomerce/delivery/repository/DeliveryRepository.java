package com.kafkaEcomerce.delivery.repository;

import org.springframework.data.repository.CrudRepository;

import com.kafkaEcomerce.delivery.entity.DeliveryEntity;

public interface DeliveryRepository extends CrudRepository<DeliveryEntity, Long> {

}