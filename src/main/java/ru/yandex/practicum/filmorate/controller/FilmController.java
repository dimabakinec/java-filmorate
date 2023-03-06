package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
@Validated
public class FilmController {
    private final Map<Integer, Film> filmById = new HashMap<>();

    private int idGenerator = 1;

    @PostMapping()
    public Film createFilm(@RequestBody @Validated Film film){
        if (filmById.values().stream().noneMatch(u -> u.getName().equals(film.getName()))) {
            film.setId(idGenerator++);
            filmById.put(film.getId(), film);
            log.error("Film with this name {} added", film.getName());
            return film;
        } else {
            log.error("Film with this name {} already exist", film.getName());
            throw new RuntimeException("Film with this name don't match");
        }
    }

    @PutMapping()
    public Film update(@RequestBody @Validated Film film) {
        if (filmById.containsKey(film.getId())) {
            filmById.put(film.getId(), film);
            return film;
        } else {
            log.error("Film with id = {} not found", film.getId());
            throw new RuntimeException("Film with this id doesnt match");
        }
    }

    @GetMapping
    public List<Film> getAllfilms() {
        return new ArrayList<>(filmById.values());
    }
}