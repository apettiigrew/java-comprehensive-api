package andrew.pettigrew.comprehensive_api.dtos;

import andrew.pettigrew.comprehensive_api.enums.InvoiceStatus;
import andrew.pettigrew.comprehensive_api.jsonapi.ResourceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto implements ResourceDto<UUID> {
    private Long id;
    private UUID userUuid;
    private LocalDate paymentDue;
    private String description;
    private Integer paymentTerms;
    private String clientName;
    private String clientEmail;
    private InvoiceStatus status;
    private float total;
    private String senderAddress;
    private String clientAddress;
}