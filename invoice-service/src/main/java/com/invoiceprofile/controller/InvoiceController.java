package com.invoiceprofile.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.invoiceprofile.request.InvoiceRequest;
import com.invoiceprofile.response.InvoiceResponse;
import com.invoiceprofile.service.InvoiceService;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    private static final Logger log = LoggerFactory.getLogger(InvoiceController.class);
    
    @PostMapping("/create")
    public InvoiceResponse create(@RequestBody InvoiceRequest invoiceRequest) {
log.info("received req from cx to create the invoice of cx : ", invoiceRequest.getCustomerName());
        InvoiceResponse response =
                invoiceService.createInvoice(invoiceRequest);
        log.info("invoice creation request has been done completely: ", invoiceRequest.getCustomerName());
        return response;
    }
    
    @GetMapping("listInvoices")
    public ResponseEntity<List<InvoiceResponse> >getAllInvoices(@RequestParam int page, @RequestParam int size) {
    	List<InvoiceResponse> response = invoiceService.getInvoices(page, size);
    	  return ResponseEntity.ok(response);
    }
}