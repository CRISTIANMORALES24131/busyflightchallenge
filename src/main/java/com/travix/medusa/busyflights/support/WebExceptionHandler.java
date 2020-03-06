package com.travix.medusa.busyflights.support;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.general.GeneralAdviceTrait;
import org.zalando.problem.spring.web.advice.http.HttpAdviceTrait;
import org.zalando.problem.spring.web.advice.io.IOAdviceTrait;
import org.zalando.problem.spring.web.advice.routing.RoutingAdviceTrait;
import org.zalando.problem.spring.web.advice.validation.ValidationAdviceTrait;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.travix.medusa.busyflights.shared.ProblemResponse;

import lombok.extern.slf4j.Slf4j;

// Web exception handler to show custom messages
@Slf4j
@ControllerAdvice
public class WebExceptionHandler
		implements GeneralAdviceTrait, HttpAdviceTrait, IOAdviceTrait, RoutingAdviceTrait, ValidationAdviceTrait {

	private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Ups! Something went wrong";

	private static final String MESSAGE_INVALID_NUMBER_FIELD = "The value '%s' is not a number";

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Problem> handleIllegalArgumentException(final NativeWebRequest req,
			final IllegalArgumentException ex) {
		return new ResponseEntity<>(createResponseProblem(ex.getMessage(), req.getDescription(false)),
				HttpStatus.BAD_REQUEST);
	}

	// Decorate all not handled exceptions to show an generic message
	// Handling this we can avoid use the class TechnicalFailureException
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Problem> handleAllExc(final NativeWebRequest req, final Exception ex) {
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(createResponseProblem(INTERNAL_SERVER_ERROR_MESSAGE, req.getDescription(false)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Decorate all not handled runtime exceptions to show an generic message
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Problem> handleAllRuntimeExc(final NativeWebRequest req, final RuntimeException ex) {
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(createResponseProblem(INTERNAL_SERVER_ERROR_MESSAGE, req.getDescription(false)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// This section override some commons REST API errors handled by Problem library
	// so it won't show stack-traces in JSON
	// responses
	// Although more are missing
	@Override
	public ResponseEntity<Problem> handleMessageNotReadableException(HttpMessageNotReadableException ex,
			NativeWebRequest req) {
		String message = ex.getMessage();
		if (ex.getCause() instanceof InvalidFormatException) {
			InvalidFormatException exInvFor = (InvalidFormatException) ex.getCause();
			if (!Number.class.isAssignableFrom(exInvFor.getTargetType())) {
				message = String.format(MESSAGE_INVALID_NUMBER_FIELD, exInvFor.getValue().toString());
			}
		}
		return new ResponseEntity<>(createResponseProblem(message, req.getDescription(false)), HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Problem> handleConstraintViolation(ConstraintViolationException ex, NativeWebRequest req) {
		return new ResponseEntity<>(createResponseProblem(ex.getMessage(), req.getDescription(false)),
				HttpStatus.BAD_REQUEST);
	}

	@Override
    public ResponseEntity<Problem> handleBindingResult(
            final BindException ex,
            final NativeWebRequest req) {
		return new ResponseEntity<>(createResponseProblem(getErrors(ex.getBindingResult()), req.getDescription(false)),
				HttpStatus.BAD_REQUEST);
    }

	@Override
	public final ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			final NativeWebRequest req) {
		return new ResponseEntity<>(createResponseProblem(getErrors(ex.getBindingResult()), req.getDescription(false)),
				HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Problem> handleMissingServletRequestPart(MissingServletRequestPartException ex,
			NativeWebRequest req) {
		return new ResponseEntity<>(createResponseProblem(ex.getMessage(), req.getDescription(false)),
				HttpStatus.BAD_REQUEST);
	}

	private String getErrors(BindingResult ex) {
		String errors;
		if (ex.hasFieldErrors()) {
			errors = ex.getFieldErrors().stream()
					.map(fieldError -> "'" + fieldError.getField() + "': " + fieldError.getDefaultMessage())
					.collect(Collectors.joining(","));
		} else {
			errors = ex.toString();
		}
		return errors;
	}

	private ProblemResponse createResponseProblem(String message, String path) {
		return ProblemResponse.builder().message(message).path(path).build();
	}

}
