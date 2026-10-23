package com.invoiceprofile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.invoiceprofile.entity.InvoiceEntity;

import jakarta.persistence.Entity;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Integer>{

}
