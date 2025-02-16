package andrew.pettigrew.comprehensive_api.jsonapi.requests;

import andrew.pettigrew.comprehensive_api.ResourceTypes;
import andrew.pettigrew.comprehensive_api.dtos.InvoiceDto;
import andrew.pettigrew.comprehensive_api.jsonapi.CreateResource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceCreateRequest implements CreateResource<InvoiceDto> {
    @Pattern(regexp = ResourceTypes.INVOICES)
    @NotNull
    private final String type = ResourceTypes.INVOICES;

    @Valid
    @NotNull
    private InvoiceDto attributes;

    @Override
    public InvoiceDto generateDto() {
        return attributes;
    }
}
