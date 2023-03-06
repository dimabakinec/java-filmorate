package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import static java.time.Month.DECEMBER;

@Slf4j
public class FilmValidator {
    public static boolean validate(Film film) throws ValidationException {
        if (film.getDuration() <= 0) {
            log.info("Film duration must be positive");
            throw new ValidationException("Film duration must be positive");
        }
        if (film.getName() == null || film.getName().equals("")) {
            log.info("Film name can't be empty.");
            throw new ValidationException("Film name can't be empty.");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, DECEMBER, 28))) {
            log.info("Film date release must be after 28.12.1985");
            throw new ValidationException("Film date release must be after 28.12.1985");
        }
        if (film.getDescription().length() > 200) {
            log.info("Description is too long");
            throw new ValidationException("Description is too long");
        }
        return false;
    }
}