package andrew.pettigrew.comprehensive_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class InvoiceRestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InvoiceErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<String>();
        Map<String,String> errorMap = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMap.put(getLastField(error.getField()),error.getDefaultMessage());
        }

        InvoiceErrorResponse error = new InvoiceErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Validation failed. Please correct the errors and try again.");
        error.setErrors(errorMap);
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    public static String getLastField(String fieldName) {
        if (fieldName == null || fieldName.isEmpty()) {
            return "";
        }
        String[] parts = fieldName.split("\\.");
        return parts[parts.length - 1];
    }

    @ExceptionHandler
    public ResponseEntity<InvoiceErrorResponse> handleException(InvoiceNotFoundException exception){
        InvoiceErrorResponse error = new InvoiceErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<InvoiceErrorResponse> handleException(Exception exception){
        InvoiceErrorResponse error = new InvoiceErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
