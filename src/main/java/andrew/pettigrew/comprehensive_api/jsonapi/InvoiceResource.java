package andrew.pettigrew.comprehensive_api.jsonapi;

import andrew.pettigrew.comprehensive_api.ResourceTypes;
import andrew.pettigrew.comprehensive_api.dtos.AddressDto;
import andrew.pettigrew.comprehensive_api.dtos.InvoiceDto;
import andrew.pettigrew.comprehensive_api.entities.Invoice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter
@ToString
@AllArgsConstructor
public class InvoiceResource implements Resource<InvoiceDto> {
    private final String type = ResourceTypes.INVOICES;
    private Long id;
    private InvoiceDto attributes;

    public static InvoiceResource toResource(final Invoice invoice){
        if(invoice == null){
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        final var modelMapper = new ModelMapper();
        final var attributes = modelMapper.map(invoice,InvoiceDto.class);
        try {
            attributes.setClientAddress(objectMapper.readValue(invoice.getClientAddress(), AddressDto.class));
            attributes.setSenderAddress(objectMapper.readValue(invoice.getSenderAddress(), AddressDto.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new InvoiceResource(invoice.getId(),attributes);
    }
}
