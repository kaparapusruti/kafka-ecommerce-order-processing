
package com.invoiceprofile.service;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.invoiceprofile.entity.InvoiceEntity;
import com.invoiceprofile.repository.InvoiceRepository;
import com.invoiceprofile.request.InvoiceRequest;
import com.invoiceprofile.response.InvoiceResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    private InvoiceRequest invoiceRequest;

    @BeforeEach
    void setUp() {
        invoiceRequest = new InvoiceRequest();
        invoiceRequest.setCustomerName("Rahul");
        invoiceRequest.setGst(18);
    }

    @Test
    void createInvoice_shouldReturnInvoiceResponse() {

        InvoiceEntity firstSave = new InvoiceEntity();
        firstSave.setId(1);
        firstSave.setCustomerName("Rahul");
        firstSave.setGst(18);
        firstSave.setStatus("paid");

        InvoiceEntity secondSave = new InvoiceEntity();
        secondSave.setId(1);
        secondSave.setCustomerName("Rahul");
        secondSave.setGst(18);
        secondSave.setStatus("paid");
        secondSave.setInvId("inv-1");

        when(invoiceRepository.save(any(InvoiceEntity.class)))
                .thenReturn(firstSave, secondSave);

        InvoiceResponse response =
                invoiceService.createInvoice(invoiceRequest);

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("inv-1", response.getInvId());
        assertEquals("Rahul", response.getCustomerName());
        assertEquals(18, response.getGst());
        assertEquals("paid", response.getStatus());

        verify(invoiceRepository, times(2))
                .save(any(InvoiceEntity.class));
    }
    

@Test
void getInvoices_shouldReturnInvoiceResponses() {

    InvoiceEntity invoice = new InvoiceEntity();
    invoice.setId(1);
    invoice.setInvId("inv-1");
    invoice.setCustomerName("Rahul");
    invoice.setGst(18);
    invoice.setStatus("paid");

    Page<InvoiceEntity> invoicePage =
            new PageImpl<>(List.of(invoice));

    when(invoiceRepository.findAll(any(Pageable.class)))
            .thenReturn(invoicePage);

    List<InvoiceResponse> responses =
            invoiceService.getInvoices(0, 10);

    assertNotNull(responses);
    assertEquals(1, responses.size());

    InvoiceResponse response = responses.get(0);

    assertEquals(1, response.getId());
    assertEquals("inv-1", response.getInvId());
    assertEquals("Rahul", response.getCustomerName());
    assertEquals(18, response.getGst());
    assertEquals("paid", response.getStatus());

    verify(invoiceRepository).findAll(any(Pageable.class));
}

}
