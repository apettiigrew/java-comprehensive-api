package andrew.pettigrew.comprehensive_api.jsonapi;

import andrew.pettigrew.comprehensive_api.ResourceTypes;
import andrew.pettigrew.comprehensive_api.dtos.InvoiceDto;
import andrew.pettigrew.comprehensive_api.entities.Invoice;
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

        final var modelMapper = new ModelMapper();
        final var attributes = modelMapper.map(invoice,InvoiceDto.class);

        return new InvoiceResource(invoice.getId(),attributes);
    }
}
