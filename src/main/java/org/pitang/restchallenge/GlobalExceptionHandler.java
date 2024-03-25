package org.pitang.restchallenge;

import org.pitang.restchallenge.dto.ErrorMessagesDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        if(ex.getFieldError().getRejectedValue() == null) {
        	ErrorMessagesDto errorMissing = new ErrorMessagesDto("Missing fields", HttpStatus.BAD_REQUEST.value());
        	return new ResponseEntity<>(errorMissing, HttpStatus.BAD_REQUEST);
        } else {
        	ErrorMessagesDto errorInvalid = new ErrorMessagesDto("Invalid fields", HttpStatus.BAD_REQUEST.value());
        	return new ResponseEntity<>(errorInvalid, HttpStatus.BAD_REQUEST);
        }

        // return super.handleMethodArgumentNotValid(ex, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		ErrorMessagesDto errorResponse = new ErrorMessagesDto("Invalid fields", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}

