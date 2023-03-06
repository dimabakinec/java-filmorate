package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import javax.validation.Valid;
import java.util.*;


@Slf4j
@RestController
@RequestMapping("/users")
@Validated

public class UserController {
    private final Map<Integer, User> userById = new HashMap<>();

    private int idGenerator = 1;

    @PostMapping()
    public User createUser(@RequestBody @Valid User user) throws ValidationException {
        UserValidator.validate(user);
        if (userById.values().stream().noneMatch(u -> u.getLogin().equals(user.getLogin()))) {
            user.setId(idGenerator++);
            userById.put(user.getId(), user);
            log.error("User with this login {} added", user.getLogin());
            return user;
        } else {
            log.error("User with this login {} already exist", user.getLogin());
            throw new RuntimeException("User with this name doesn't match");
        }
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        if (userById.containsKey(user.getId())) {
            userById.put(user.getId(), user);
            return user;
        } else {
            log.error("User with id = {} not found", user.getId());
            throw new RuntimeException("User with this id doesn't match");
        }
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return new ArrayList<>(userById.values());
    }
}
