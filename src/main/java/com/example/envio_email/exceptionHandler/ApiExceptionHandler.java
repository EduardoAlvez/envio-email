package com.example.envio_email.exceptionHandler;

import com.example.envio_email.exceptionHandler.dto.ErrorResponse;
import com.example.envio_email.service.exceptions.FalhaEnvioEmailException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handler para erros de validação @Valid
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        List<ErrorResponse.FieldError> fieldErrors = new ArrayList<>();

        for (FieldError error : bindingResult.getFieldErrors()) {
            String rejectedValue = error.getRejectedValue() != null ? error.getRejectedValue().toString() : null;
            fieldErrors.add(new ErrorResponse.FieldError(
                    error.getField(),
                    error.getDefaultMessage(),
                    rejectedValue
            ));
        }

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                "Erro de validação nos campos enviados",
                getPath(request),
                fieldErrors
        );

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    /**
     * Handler para erros de validação em parâmetros de URL/Query
     */
    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(
            HandlerMethodValidationException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        // Mensagem genérica para erro de validação de parâmetros
        String mensagem = "Erro de validação nos parâmetros da requisição";

        // Tenta extrair a mensagem de erro
        if (ex.getMessage() != null) {
            mensagem = ex.getMessage();
        }

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                mensagem,
                getPath(request),
                null
        );
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    /**
     * Handler para erros de JSON mal formatado
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String mensagemErro = "JSON inválido ou mal formatado";

        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException formatException = (InvalidFormatException) ex.getCause();
            if (formatException.getTargetType() != null && formatException.getTargetType().isEnum()) {
                String fieldName = formatException.getPath().isEmpty()
                        ? "campo desconhecido"
                        : formatException.getPath().get(0).getFieldName();
                mensagemErro = String.format("Valor inválido para o campo '%s'. Valores aceitos: %s",
                        fieldName,
                        Arrays.toString(formatException.getTargetType().getEnumConstants()));
            }
        }

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                mensagemErro,
                getPath(request),
                null
        );

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    /**
     * Handler para exceção de falha no envio de email
     */
    @ExceptionHandler(FalhaEnvioEmailException.class)
    public ResponseEntity<ErrorResponse> handleFalhaEnvioEmailException(
            FalhaEnvioEmailException ex,
            WebRequest request) {

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                ex.getMessage(),
                getPath(request),
                null
        );

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    /**
     * Handler para IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex,
            WebRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                ex.getMessage(),
                getPath(request),
                null
        );

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    /**
     * Handler para exceções genéricas (fallback)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            WebRequest request) {

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                "Ocorreu um erro interno no servidor. Tente novamente mais tarde.",
                getPath(request),
                null
        );

        // Log do erro completo (importante para debug)
        logger.error("Erro não tratado: ", ex);

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    /**
     * Handler para NullPointerException
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(
            NullPointerException ex,
            WebRequest request) {

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                "Erro interno: dado obrigatório não encontrado",
                getPath(request),
                null
        );

        logger.error("NullPointerException: ", ex);

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    /**
     * Método auxiliar para extrair o path da requisição
     */
    private String getPath(WebRequest request) {
        String description = request.getDescription(false);
        return description.replace("uri=", "");
    }
}