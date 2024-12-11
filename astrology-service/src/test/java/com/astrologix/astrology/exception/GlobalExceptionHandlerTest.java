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
        // Mock the BindingResult
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors())
                .thenReturn(List.of(new FieldError("objectName", "fieldName", "Field is required")));

        // Mock MethodArgumentNotValidException
        MethodArgumentNotValidException mockException = mock(MethodArgumentNotValidException.class);
        when(mockException.getBindingResult()).thenReturn(bindingResult);

        // Call the handler
        ErrorResponse response = exceptionHandler.handleValidationExceptions(mockException, request);

        // Assertions
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("Validation Error", response.getError());
        assertEquals("fieldName: Field is required", response.getMessage());
        assertEquals("/api/test", response.getPath());
    }

    @Test
    @DisplayName("Handle ConstraintViolationException")
    void testHandleConstraintViolationException() {
        // Mock the property path
        Path mockPath = mock(Path.class);
        when(mockPath.toString()).thenReturn("fieldName");

        // Mock ConstraintViolation
        ConstraintViolation<?> mockViolation = mock(ConstraintViolation.class);
        when(mockViolation.getPropertyPath()).thenReturn(mockPath);
        when(mockViolation.getMessage()).thenReturn("must not be null");

        // Create a set of violations
        Set<ConstraintViolation<?>> violations = Set.of(mockViolation);

        // Mock ConstraintViolationException
        ConstraintViolationException exception = new ConstraintViolationException(violations);

        // Call the exception handler
        ErrorResponse response = exceptionHandler.handleConstraintViolationException(exception, request);

        // Assertions
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

    // Helper method to mock ConstraintViolation
    private ConstraintViolation<?> mockViolation(String path, String message) {
        jakarta.validation.ConstraintViolation<?> mockViolation = mock(jakarta.validation.ConstraintViolation.class);
        when(mockViolation.getPropertyPath().toString()).thenReturn(path);
        when(mockViolation.getMessage()).thenReturn(message);
        return mockViolation;
    }
}

