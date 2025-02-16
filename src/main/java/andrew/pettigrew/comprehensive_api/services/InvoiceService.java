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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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


    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
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

    public Invoice updateInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice); // Save handles both insert and update based on ID
    }

    public void deleteInvoice(Integer id) {
        invoiceRepository.deleteById(id);
    }
}
