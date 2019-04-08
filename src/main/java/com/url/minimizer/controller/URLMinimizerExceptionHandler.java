package com.url.minimizer.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.url.minimizer.dto.ErrorInfoDTO;
import com.url.minimizer.exception.URLMinimizerBaseException;

@ControllerAdvice
public class URLMinimizerExceptionHandler {

	@ExceptionHandler({URLMinimizerBaseException.class})
	@ResponseBody
	public ResponseEntity<ErrorInfoDTO> urlException(URLMinimizerBaseException e) {
		ErrorInfoDTO errorInfoDTO = new ErrorInfoDTO(e);
		return ResponseEntity
				.status(e.getStatusCode())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(errorInfoDTO);
	}
}
