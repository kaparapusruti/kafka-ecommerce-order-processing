package com.invoiceprofile.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.invoiceprofile.controller.InvoiceController;
import com.invoiceprofile.entity.InvoiceEntity;
import com.invoiceprofile.repository.InvoiceRepository;
import com.invoiceprofile.request.InvoiceRequest;
import com.invoiceprofile.response.InvoiceResponse;

@Service
public class InvoiceService {
	@Autowired
	InvoiceRepository invoiceRepository;
	
	  private static final Logger log = LoggerFactory.getLogger(InvoiceService.class);
	
// It removes outdated cached data when you create or update an invoice.\
	//This is called cache invalidation
@CacheEvict(value="MyDataBase", allEntries=true)
	public InvoiceResponse createInvoice(InvoiceRequest invoiceRequest) {

		InvoiceEntity entity = new InvoiceEntity();

		entity.setCustomerName(invoiceRequest.getCustomerName());
		entity.setGst(invoiceRequest.getGst());
	
		entity.setStatus("paid");
	    // First save -> DB generates id
	    entity = invoiceRepository.save(entity);
	    // Now id is available
	    entity.setInvId("inv-" + entity.getId());

		entity = invoiceRepository.save(entity);
		
		InvoiceResponse response = new InvoiceResponse();
		if(entity.getId() > 0) {
			response.setCustomerName(entity.getCustomerName());
			response.setGst(entity.getGst());
			response.setId(entity.getId());
			response.setInvId(entity.getInvId());
			response.setStatus(entity.getStatus());
			
			
		}
		return response;

	}
	@Cacheable(value="MyDataBase", key="#page + '-' + #size")
	public List<InvoiceResponse> getInvoices(int page , int size){
		
		log.info("fetching invoices from databases....",page,size);
		log.debug("fetching invoices from db");
		
		Pageable pageable = PageRequest.of(page, size);
		Page<InvoiceEntity> result =  invoiceRepository.findAll(pageable);
		
		List<InvoiceResponse> response = new ArrayList<InvoiceResponse>();
		
		for(InvoiceEntity invoiceEntity : result) {
			 InvoiceResponse response1 = new InvoiceResponse();
			 
			 response1.setId(invoiceEntity.getId());
			 response1.setInvId(invoiceEntity.getInvId());
			 response1.setCustomerName(invoiceEntity.getCustomerName());
			 response1.setGst(invoiceEntity.getGst());
			 response1.setStatus(invoiceEntity.getStatus());

			 response.add(response1);
			 log.info("invoices data got added from databases....",page,size);
		}
		
		return response;
		
	}

}
