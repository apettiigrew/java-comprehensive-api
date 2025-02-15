package andrew.pettigrew.comprehensive_api.controllers;

import andrew.pettigrew.comprehensive_api.ResourceTypes;
import andrew.pettigrew.comprehensive_api.dtos.InvoiceDto;
import andrew.pettigrew.comprehensive_api.jsonapi.JsonApiConstants;
import andrew.pettigrew.comprehensive_api.jsonapi.requests.CreateRequest;
import andrew.pettigrew.comprehensive_api.jsonapi.requests.InvoiceCreateRequest;
import andrew.pettigrew.comprehensive_api.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/" + ResourceTypes.INVOICES, produces = JsonApiConstants.JSON_API_CONTENT_TYPE)
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;


    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<InvoiceDto> createInvoice(final @RequestBody @Validated CreateRequest<InvoiceCreateRequest> requestData) {

        InvoiceDto invoiceDto = requestData.getData().generateDto();
//        Invoice createdInvoice = invoiceService.createInvoice(invoiceDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceDto);
    }
}