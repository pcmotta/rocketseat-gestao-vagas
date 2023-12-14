package br.com.attomtech.gestaovagas.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

@ControllerAdvice
public class ExceptionHandlerController {

    MessageSource messageSource;

    public ExceptionHandlerController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var dto = new ArrayList<ErrorMessageDTO>();

        e.getBindingResult().getFieldErrors().forEach(err -> {
            var message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
            var error = new ErrorMessageDTO(message, err.getField());
            dto.add(error);
        });

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    record ErrorMessageDTO(String message, String field) {};
}
