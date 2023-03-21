package ru.yandex.practicum.filmorate.storage.film;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class InMemoryFilmStorage implements FilmStorage {
    static final int MAX_LENGTH_DESCRIPTION = 200;
    static final int MIN_DURATION = 0;
    static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    long nextId = 1;
    final Map<Long, Film> films = new HashMap<>();

    public Map<Long, Film> getFilms() {
        return films;
    }

    @Override
    public List<Film> findMostPopularFilms(int countFilms) {
        return null;
    }

    public Film addNewFilm(Film film) {
        if (validateFilmFields(film)) {
            film.setId(nextId++);
            film.setUsersWhoLiked(new HashSet<>());
            films.put(film.getId(), film);
            log.info("Added new movie with id: " + film.getId());
        } else {
            log.info("Fields filled out incorrectly");
        }
        return film;
    }

    public Film updateFilm(Film film) {
        if (validateFilmFields(film)) {
            if (!films.containsKey(film.getId())) {
                log.debug("Movie with this id does not exist");
                throw new NotFoundException("Movie with this id does not exist");
            }
            films.put(film.getId(), film);
            log.info("Movie with id: " + film.getId() + " successfully updated");
        } else {
            log.info("Fields filled out incorrectly");
        }
        if (film.getUsersWhoLiked() == null) {
            film.setUsersWhoLiked(new HashSet<>());
        }
        return film;
    }

    public Collection<Film> findFilms() {
        return films.values();
    }

    public Film findFilm(long filmId) {
        if (films.containsKey(filmId)) {
            return films.get(filmId);
        } else {
            throw new NotFoundException("Movie with this id does not exist");
        }
    }


    public boolean validateName(Film film) {
        if (film.getName() != null && film.getName().length() > 0) {
            return  true;
        } else {
            throw new ValidationException("Movie title cannot be empty");
        }
    }

    public boolean validateDescription(Film film) {
        if (film.getDescription().length() <= MAX_LENGTH_DESCRIPTION) {
            return true;
        } else {
            throw new ValidationException("Maximum description length - 200 characters");
        }
    }

    public boolean validateReleaseDate(Film film) {
        if (!film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            return true;
        } else {
            throw new ValidationException("The release date of the film cannot be earlier than 28.12.1895");
        }
    }

    public boolean validateDuration(Film film) {
        if (film.getDuration() > MIN_DURATION) {
            return true;
        } else {
            throw new ValidationException("Movie length must be greater than 0");
        }
    }

    public boolean validateFilmFields(Film film) {
        return validateName(film) && validateDescription(film) && validateReleaseDate(film) && validateDuration(film);
    }
}