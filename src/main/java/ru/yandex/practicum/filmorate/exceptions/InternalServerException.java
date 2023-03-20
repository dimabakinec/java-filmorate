package ru.yandex.practicum.filmorate.exceptions;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String s) {
        super(s);
    }
}
