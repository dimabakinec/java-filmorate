package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserValidation {

    boolean validateEmail(User user) {
        if (user.getEmail() == null || user.getEmail().length() == 0) {
            throw new ValidationException("Email field cannot be empty");
        } else if (!user.getEmail().contains("@")) {
            throw new ValidationException("The Email field must contain the @ symbol");
        } else {
            return true;
        }
    }

    boolean validateLogin(User user) {
        if (user.getLogin() == null || user.getLogin().length() == 0) {
            throw new ValidationException("Login field cannot be empty");
        } else if (user.getLogin().contains(" ")) {
            throw new ValidationException("The Login field cannot contain spaces");
        } else {
            return true;
        }
    }

    boolean validateBirthday(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Date of birth cannot be greater than the current date");
        } else {
            return true;
        }
    }

    public boolean validateUserFields(User user) {
        return validateEmail(user) && validateLogin(user) && validateBirthday(user);
    }
}
