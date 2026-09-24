package com.kafkaEcomerce.payment.repository;

import org.springframework.data.repository.CrudRepository;

import com.kafkaEcomerce.payment.entity.PaymentEntity;

public interface PaymentRepository extends CrudRepository<PaymentEntity, Long> {

}
