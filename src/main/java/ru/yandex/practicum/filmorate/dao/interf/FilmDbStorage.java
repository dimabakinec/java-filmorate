package ru.yandex.practicum.filmorate.dao.interf;

import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;
import java.util.Optional;

public interface FilmDbStorage {

    Film create(Film film);

    @SuppressWarnings("checkstyle:EmptyLineSeparator")
    void delete(Film film);

    Film update(Film film);

    @SuppressWarnings("checkstyle:GenericWhitespace")
    List < Film > getFilms();

    @SuppressWarnings("checkstyle:GenericWhitespace")
    Optional < Film > getById(long id);
}
