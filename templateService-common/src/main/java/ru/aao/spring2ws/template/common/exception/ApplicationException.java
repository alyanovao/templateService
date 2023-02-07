package ru.aao.spring2ws.template.common.exception;

public class ApplicationException extends RuntimeException {
    public ApplicationException() {
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
