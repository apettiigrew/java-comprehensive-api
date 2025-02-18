package andrew.pettigrew.comprehensive_api.exceptions;

import lombok.Data;

@Data
public class InvoiceErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
