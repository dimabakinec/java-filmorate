package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTest {
    private static FilmController filmController;
    private static Film film;

    @BeforeEach
    public void beforeEach() {
        filmController = new FilmController(new FilmService(new InMemoryFilmStorage()));
        film = createFilm();
    }

    private Film createFilm() {
        return Film.builder()
                .description("DescriptionFilm")
                .name("NameFilm")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(180)
                .usersWhoLiked(new HashSet<>())
                .build();
    }

    @Test
    void validateDescException() {
        film.setDescription("Trainspotting is a 1996 British black comedy-drama film " +
                "directed by Danny Boyle and starring Ewan McGregor, Ewen Bremner, " +
                "Jonny Lee Miller, Kevin McKidd, Robert Carlyle, and Kelly Macdonald " +
                "in her film debut. Based on the 1993 novel of the same title by " +
                "Irvine Welsh, the film was released in the United Kingdom on 23 " +
                "February 1996.");
        assertThrows(ValidationException.class, () -> filmController.addNewFilm(film), "without desc exception");
    }

    @Test
    void validateDurationException() {
        film.setDuration(0);
        assertThrows(ValidationException.class, () -> filmController.addNewFilm(film), "without duration exception");
    }

    @Test
    void validateNameException() {
        film.setName("");
        assertThrows(ValidationException.class, () -> filmController.addNewFilm(film), "without name exception");
    }

    @Test
    void validateReleaseDateException() {
        film.setReleaseDate(LocalDate.of(1800, 1,1));
        assertThrows(ValidationException.class, () -> filmController.addNewFilm(film), "without name exception");
    }

    @Test
    void validateUpdateNotExistFilmTest() {
        filmController.addNewFilm(film);
        film.setId(2);
        assertThrows(NotFoundException.class, () -> filmController.updateFilm(film), "without exception");
    }
}
