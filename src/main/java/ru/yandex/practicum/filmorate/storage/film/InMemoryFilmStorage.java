package ru.yandex.practicum.filmorate.storage.film;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.FilmValidation;

import java.util.*;

@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
@Qualifier("inMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {
    final FilmValidation filmValidation = new FilmValidation();
    long nextId = 1;
    final Map<Long, Film> films = new HashMap<>();

    public Map<Long, Film> getFilms() {
        return films;
    }

    public Film addNewFilm(Film film) {
        if (filmValidation.validateFilmFields(film)) {
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
        if (filmValidation.validateFilmFields(film)) {
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
}
