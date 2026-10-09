package com.pt.clientes.controller;

import com.pt.clientes.rest.model.ModelResponseDto;
import com.pt.clientes.services.exception.GeneralException;
import com.pt.clientes.services.messages.ExceptionMessages;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tools.jackson.databind.exc.InvalidFormatException;

import java.util.Arrays;
import java.util.List;


@RestControllerAdvice
public class AdviceRestController extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ModelResponseDto response = ModelResponseDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .message(ExceptionMessages.ERROR_VALIDACIONES_REQUEST + String.join(", ", errors))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = "";

        if (ex.getCause() instanceof InvalidFormatException ife &&
                ife.getTargetType().isEnum()) {
            message = ExceptionMessages.ERROR_GENERO_ENUM + ife.getTargetType().getSimpleName()
                    + ". Valores permitidos: "
                    + String.join(", ",
                    Arrays.stream(ife.getTargetType().getEnumConstants())
                            .map(Object::toString)
                            .toList());
        }

        ModelResponseDto response = ModelResponseDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .message(message)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ExceptionHandler(value = GeneralException.class)
    public ModelResponseDto handleApiAccess(GeneralException exception) {
        return ModelResponseDto.builder()
                .statusCode(HttpStatus.NOT_ACCEPTABLE)
                .message(exception.getMessage())
                .build();
    }


}
