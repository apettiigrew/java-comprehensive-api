package andrew.pettigrew.comprehensive_api.controllers;

import andrew.pettigrew.comprehensive_api.ResourceTypes;
import andrew.pettigrew.comprehensive_api.dtos.InvoiceDto;
import andrew.pettigrew.comprehensive_api.entities.Invoice;
import andrew.pettigrew.comprehensive_api.enums.InvoiceStatus;
import andrew.pettigrew.comprehensive_api.jsonapi.InvoiceResource;
import andrew.pettigrew.comprehensive_api.jsonapi.JsonApiConstants;
import andrew.pettigrew.comprehensive_api.jsonapi.MultipleResourceResponse;
import andrew.pettigrew.comprehensive_api.jsonapi.SingleResourceResponse;
import andrew.pettigrew.comprehensive_api.jsonapi.requests.CreateRequest;
import andrew.pettigrew.comprehensive_api.jsonapi.requests.InvoiceCreateRequest;
import andrew.pettigrew.comprehensive_api.jsonapi.requests.UpdateRequest;
import andrew.pettigrew.comprehensive_api.services.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/" + ResourceTypes.INVOICES, produces = JsonApiConstants.JSON_API_CONTENT_TYPE)
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public SingleResourceResponse<InvoiceResource> createInvoice(final @Valid @RequestBody CreateRequest<InvoiceCreateRequest> requestData) {

        InvoiceDto invoiceDto = requestData.getData().generateDto();
        Invoice savedInvoice = invoiceService.createInvoice(invoiceDto);

        return new SingleResourceResponse<>(InvoiceResource.toResource(savedInvoice));
    }

    @GetMapping
    public MultipleResourceResponse<InvoiceResource> getAllInvoices(
            @PageableDefault(size = 10, direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "status", required = false) InvoiceStatus status) {
        Page<Invoice> invoices = invoiceService.getAllInvoices(pageable,status);

        final Page<InvoiceResource> invoiceResourcePage = new PageImpl<>(
                invoices.getContent()
                        .stream()
                        .map(InvoiceResource::toResource)
                        .collect(Collectors.toList()),
                invoices.getPageable(),
                invoices.getTotalElements()
        );
        return new MultipleResourceResponse<>(invoiceResourcePage);
    }

    @GetMapping("/{id}")
    public SingleResourceResponse<InvoiceResource> getInvoiceById(final @PathVariable("id") Integer id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        return new SingleResourceResponse<>(InvoiceResource.toResource(invoice));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public SingleResourceResponse<InvoiceResource> updateUser(final @PathVariable Integer id, @RequestBody @Validated UpdateRequest<InvoiceCreateRequest> requestData) {
        InvoiceDto invoiceDto = requestData.getData().generateDto();

        Invoice updatedInvoice = invoiceService.updateInvoice(id, invoiceDto);
        return new SingleResourceResponse<>(InvoiceResource.toResource(updatedInvoice));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(final @PathVariable Integer id) {
        try {
            invoiceService.deleteInvoice(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}