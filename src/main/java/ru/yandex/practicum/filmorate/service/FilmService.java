package ru.yandex.practicum.filmorate.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilmService {
    public final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film addNewFilm(Film film) {
        log.info("Запрос на добавление нового фильма");
        return filmStorage.addNewFilm(film);
    }

    public Film updateFilm(Film film) {
        log.info("Запрос на обновление данных фильма");
        return filmStorage.updateFilm(film);
    }

    public Film addOrDeleteLikeToFilm(long filmId, long userId, String typeOperation) {
        if (filmStorage.getFilms().containsKey(filmId)) {
            Film film = filmStorage.findFilm(filmId);
            Set<Long> newSetWithLikes = film.getUsersWhoLiked();
            switch (typeOperation) {
                case ("DELETE"):
                    if (newSetWithLikes.contains(userId)) {
                        newSetWithLikes.remove(userId);
                    } else {
                        throw new NotFoundException("This user did not like this movie, deletion is impossible");
                    }
                    log.info("Removed like from user");
                    break;
                case ("ADD"):
                    newSetWithLikes.add(userId);
                    log.info("Added like from user");
                    break;
                default:
                    break;
            }
            film.setUsersWhoLiked(newSetWithLikes);
            return film;
        } else {
            throw new NotFoundException("Movie with this id was not found");
        }
    }

    public List<Film> findMostPopularFilms(int countFilms) {
        return filmStorage.findFilms().stream()
                .sorted((o1, o2) -> (int) (o2.getCountLikes() - o1.getCountLikes()))
                .limit((countFilms))
                .collect(Collectors.toList());
    }
}
