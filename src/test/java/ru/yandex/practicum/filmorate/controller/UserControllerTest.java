package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {
    private static UserController userController;
    private static User user;
    @BeforeEach
    public void BeforeEach() {
        userController = new UserController(new UserService(new InMemoryUserStorage()));
        user = createUser();
    }

    private User createUser() {
        return User.builder()
                .email("dmitry@yandex.ru")
                .login("LoginUser")
                .name("NameUser")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
    }

    @Test
    void validateLoginTest() {
        user.setLogin("");
        assertThrows(ValidationException.class, () -> userController.addNewUser(user), "without login exception");
    }

    @Test
    void validateBirthdayTest() {
        user.setBirthday(LocalDate.of(2025, 1, 1));
        assertThrows(ValidationException.class, () -> userController.addNewUser(user), "without birthday exception");
    }

    @Test
    void validateEmailTest() {
        user.setEmail("mymail.ru");
        assertThrows(ValidationException.class, () -> userController.addNewUser(user), "without email exception");
    }

    @Test
    void validateAlreadyExistUserTest() {
        userController.addNewUser(user);
        user.setId(2);
        assertThrows(NotFoundException.class, () -> userController.updateUser(user), "without exception");
    }
}
