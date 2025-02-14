package andrew.pettigrew.comprehensive_api.services;

import andrew.pettigrew.comprehensive_api.dtos.InvoiceDto;
import andrew.pettigrew.comprehensive_api.entities.Invoice;
import andrew.pettigrew.comprehensive_api.respositories.InvoiceRepository;
import andrew.pettigrew.comprehensive_api.respositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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
        Invoice invoice = modelMapper.map(invoiceDto,Invoice.class);
        var user = invoice.getUser();
        List<Invoice> userInvoices = user.getInvoices();

        if (userInvoices == null) {
            var listOfInvoice = new ArrayList<Invoice>();
            listOfInvoice.add(invoice);
            user.setInvoices(listOfInvoice);
        } else {
            userInvoices.add(invoice);
            userRepository.save(user);
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
