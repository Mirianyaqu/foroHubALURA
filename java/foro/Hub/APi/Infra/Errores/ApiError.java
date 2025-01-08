package foro.Hub.APi.Infra.Errores;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

    class ApiError {
        private final HttpStatus status;
        private final String message;
        private final LocalDateTime timestamp;
        private final List<String> fieldErrors;
        private final String errorCode;


        public ApiError(HttpStatus status, String message, MethodArgumentNotValidException ex) {
            this.status = status;
            this.message = message;
            this.timestamp = LocalDateTime.now();
            this.fieldErrors = extractFieldErrors(ex);
            this.errorCode = generateErrorCode(status);
        }

        private List<String> extractFieldErrors(MethodArgumentNotValidException ex) {
            return ex.getBindingResult().getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .collect(Collectors.toList());
        }

        private String generateErrorCode(HttpStatus status) {
            return status.toString() + "_" + LocalDateTime.now().toString();
        }


        public HttpStatus getStatus() {
            return this.status;
        }

        public String getMessage() {
            return message;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public List<String> getFieldErrors() {
            return fieldErrors;
        }

        public String getErrorCode() {
            return errorCode;
        }


}
