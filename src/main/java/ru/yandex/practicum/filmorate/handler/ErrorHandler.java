package ru.yandex.practicum.filmorate.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.Map;

@RestControllerAdvice(basePackages = "ru.yandex.practicum.filmorate.controller")
public class ErrorHandler {

    @SuppressWarnings("checkstyle:GenericWhitespace")
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String > handleValidationException(final ValidationException validationException) {
        return Map.of("error", validationException.getMessage());
    }

    @SuppressWarnings("checkstyle:GenericWhitespace")
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String > handleNotFoundException(final NotFoundException notFoundException) {
        return Map.of("error", notFoundException.getMessage());
    }

    @SuppressWarnings("checkstyle:GenericWhitespace")
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String > handleException(final Throwable exception) {
        return Map.of("error", exception.getMessage());
    }
}