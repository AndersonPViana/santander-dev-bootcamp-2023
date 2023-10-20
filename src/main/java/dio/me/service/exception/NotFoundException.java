package dio.me.service.exception;

public class NotFoundException extends BusinessException{
    public NotFoundException(String message) {
        super(message != null ? message : "Resource not found");
    }
}
