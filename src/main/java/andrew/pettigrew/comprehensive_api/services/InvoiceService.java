package andrew.pettigrew.comprehensive_api.services;

import andrew.pettigrew.comprehensive_api.dtos.InvoiceDto;
import andrew.pettigrew.comprehensive_api.entities.Invoice;
import andrew.pettigrew.comprehensive_api.entities.User;
import andrew.pettigrew.comprehensive_api.respositories.InvoiceRepository;
import andrew.pettigrew.comprehensive_api.respositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    @Qualifier("skipNullModelMapper")
    private ModelMapper modelMapper;


    public Page<Invoice> getAllInvoices(Pageable pageable ) {
        return invoiceRepository.findAll(pageable);
    }

    public Invoice getInvoiceById(Integer id) {
        return invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    public Invoice createInvoice(InvoiceDto invoiceDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = userRepository.findByUuid(UUID.fromString(invoiceDto.getUserUuid())).orElseThrow(() -> new RuntimeException("User not found"));

        Invoice invoice = modelMapper.map(invoiceDto, Invoice.class);
        invoice.setUser(user);

        try {
            invoice.setSenderAddress(objectMapper.writeValueAsString(invoiceDto.getSenderAddress()));
            invoice.setClientAddress(objectMapper.writeValueAsString(invoiceDto.getClientAddress()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return invoiceRepository.save(invoice);
    }

    public Invoice updateInvoice(Integer id, InvoiceDto invoiceDto) {
        Invoice existingInvoice = invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));
        ObjectMapper objectMapper = new ObjectMapper();
        modelMapper.map(invoiceDto,existingInvoice);
        try {
            existingInvoice.setSenderAddress(objectMapper.writeValueAsString(invoiceDto.getSenderAddress()));
            existingInvoice.setClientAddress(objectMapper.writeValueAsString(invoiceDto.getClientAddress()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

       return invoiceRepository.save(existingInvoice);
    }

    public void deleteInvoice(Integer id) {
        invoiceRepository.deleteById(id);
    }
}
