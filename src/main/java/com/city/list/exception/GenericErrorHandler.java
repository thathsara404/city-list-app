package com.city.list.exception;

import com.city.list.enums.ErrorMessage;
import com.city.list.dto.ErrorResponseDTO;
import com.city.list.dto.ResponseDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handle exception and build error response.
 * */
@ControllerAdvice
public class GenericErrorHandler {

    //TODO: put some logs with the exception object with AOP Logging

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDTO> generateResourceNotFoundResponse(ResourceNotFoundException resourceNotFoundException) {
        ResponseDTO responseDTO = new ResponseDTO(null, new ErrorResponseDTO(ErrorMessage.NOT_FOUND.toString()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @ExceptionHandler(UnExpectedErrorOccurredException.class)
    public ResponseEntity<ResponseDTO> generateUnexpectedErrorOccurredExceptionResponse(UnExpectedErrorOccurredException unExpectedErrorOccurredException) {
        ResponseDTO responseDTO = new ResponseDTO(null, new ErrorResponseDTO(ErrorMessage.INTERNAL_SERVER_ERROR.toString()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDTO> generateUnexpectedErrorOccurredExceptionResponse(HttpMessageNotReadableException httpMessageNotReadableException) {
        ResponseDTO responseDTO = new ResponseDTO(null, new ErrorResponseDTO(ErrorMessage.BAD_REQUEST.toString()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDTO> generateUnexpectedErrorOccurredExceptionResponse(DataIntegrityViolationException dataIntegrityViolationException) {
        ResponseDTO responseDTO = new ResponseDTO(null, new ErrorResponseDTO(ErrorMessage.DATA_DUPLICATION_ERROR.toString()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDTO);
    }

    @ExceptionHandler(CanNotProceedTransactionException.class)
    public ResponseEntity<ResponseDTO> generateTransactionExceptionResponse(CanNotProceedTransactionException dataIntegrityViolationException) {
        ResponseDTO responseDTO = new ResponseDTO(null, new ErrorResponseDTO(dataIntegrityViolationException.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDTO);
    }

}
