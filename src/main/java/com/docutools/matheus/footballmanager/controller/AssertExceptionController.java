package com.docutools.matheus.footballmanager.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@RestControllerAdvice
public class AssertExceptionController  extends DefaultHandlerExceptionResolver {
	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity<?> handleAssertException(Exception exception, WebRequest request) {
		String message;
		Throwable cause, resultCause = exception;
		while ((cause = resultCause.getCause()) != null && resultCause != cause) {
			resultCause = cause;
		}
		if (resultCause instanceof ConstraintViolationException) {
			message = (((ConstraintViolationException) resultCause).getConstraintViolations()).iterator().next().getMessage();
		} else {
			resultCause.printStackTrace();
			message = "Unknown error";
		}
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		HashMap<String, String> errorMessage = new HashMap<>();
		errorMessage.put("timestamp",formatter.format(date));
		errorMessage.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
		errorMessage.put("error",HttpStatus.BAD_REQUEST.getReasonPhrase());
		errorMessage.put("message",message);
		errorMessage.put("path",((ServletWebRequest)request).getRequest().getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}
}
