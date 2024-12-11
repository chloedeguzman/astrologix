package com.astrologix.astrology.exception;

import com.astrologix.astrology.dto.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;
    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
        request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
    }

    @Test
    @DisplayName("Handle MethodArgumentNotValidException")
    void shouldHandleValidationExceptions() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors())
                .thenReturn(List.of(new FieldError("objectName", "fieldName", "Field is required")));

        MethodArgumentNotValidException mockException = mock(MethodArgumentNotValidException.class);
        when(mockException.getBindingResult()).thenReturn(bindingResult);

        ErrorResponse response = exceptionHandler.handleValidationExceptions(mockException, request);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("Validation Error", response.getError());
        assertEquals("fieldName: Field is required", response.getMessage());
        assertEquals("/api/test", response.getPath());
    }

    @Test
    @DisplayName("Handle ConstraintViolationException")
    void shouldHandleConstraintViolationException() {
        Path mockPath = mock(Path.class);
        when(mockPath.toString()).thenReturn("fieldName");

        ConstraintViolation<?> mockViolation = mock(ConstraintViolation.class);
        when(mockViolation.getPropertyPath()).thenReturn(mockPath);
        when(mockViolation.getMessage()).thenReturn("must not be null");

        Set<ConstraintViolation<?>> violations = Set.of(mockViolation);

        ConstraintViolationException exception = new ConstraintViolationException(violations);

        ErrorResponse response = exceptionHandler.handleConstraintViolationException(exception, request);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("Validation Error", response.getError());
        assertEquals("fieldName: must not be null", response.getMessage());
        assertEquals("/api/test", response.getPath());
    }

    @Test
    @DisplayName("Handle IllegalArgumentException")
    void shouldHandleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");

        ErrorResponse response = exceptionHandler.handleIllegalArgumentException(exception, request);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("Invalid Input", response.getError());
        assertEquals("Invalid argument", response.getMessage());
        assertEquals("/api/test", response.getPath());
    }

    @Test
    @DisplayName("Handle MissingServletRequestParameterException")
    void shouldHandleMissingRequestParameterException() {
        MissingServletRequestParameterException exception = new MissingServletRequestParameterException("param", "String");

        Map<String, Object> response = exceptionHandler.handleMissingRequestParameterException(exception);

        assertEquals("Validation Error", response.get("error"));
        assertEquals("Required request parameter is missing", response.get("message"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.get("status"));
    }

    @Test
    @DisplayName("Handle Generic Exception")
    void shouldHandleGenericException() {
        Exception exception = new Exception("Something went wrong");

        ErrorResponse response = exceptionHandler.handleGenericException(exception, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("Internal Server Error", response.getError());
        assertEquals("Something went wrong", response.getMessage());
        assertEquals("/api/test", response.getPath());
    }

}

