package com.lcwd.electronic.store.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.lcwd.electronic.store.dtos.ApiResponceMessage;

@RestControllerAdvice

public class GlobalExceptionHandler {
	
	private Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(ResourseNotFoundException.class)
	public ResponseEntity<ApiResponceMessage> resourseNotFoundExceptionHandler(ResourseNotFoundException ex)
	{
		logger.info("Exception handler invoked !!");
		ApiResponceMessage response=ApiResponceMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
		return new ResponseEntity<ApiResponceMessage>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
	{
		List<ObjectError> alleErrors=ex.getBindingResult().getAllErrors();
		Map<String, Object> response=new HashMap<>();
		alleErrors.stream().forEach(objectError -> {
			String message=objectError.getDefaultMessage();
			String field=((FieldError)objectError).getField();
			response.put(field, message);
		});
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PropertyReferenceException.class)
	public ResponseEntity<ApiResponceMessage> handlePropertyReferenceException(PropertyReferenceException ex)
	{
		logger.info("Exception handler invoked !!");
		ApiResponceMessage response=ApiResponceMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
		return new ResponseEntity<ApiResponceMessage>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadApiRequest.class)
	public ResponseEntity<ApiResponceMessage> badApiResourceExceptionHandler(BadApiRequest ex)
	{
		logger.info("Bad API request !!");
		ApiResponceMessage response=ApiResponceMessage.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();
		return new ResponseEntity<ApiResponceMessage>(response,HttpStatus.BAD_REQUEST);
	}

}
