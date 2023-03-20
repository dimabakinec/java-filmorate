package ru.yandex.practicum.filmorate.storage.user;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class InMemoryUserStorage implements UserStorage {
    long nextId = 1;
    final Map<Long, User> users = new HashMap<>();

    public Map<Long, User> getUsers() {
        return users;
    }
    public User addNewUser(User user) {
        if (user.getName() == null || Objects.equals(user.getName(), "") || Objects.equals(user.getName(), " ")) {
            user.setName(user.getLogin());
        }
        if (validateUserFields(user)) {
            user.setId(nextId++);
            user.setFriendsIdsSet(new HashSet<>());
            users.put(user.getId(), user);
            log.info("Added new user with id: " + user.getId());
        } else {
            log.info("Fields filled out incorrectly");
        }
        return user;
    }

    public User updateUser(User user) {
        if (validateUserFields(user)) {
            if (!users.containsKey(user.getId())) {
                log.debug("User with this id does not exist");
                throw new NotFoundException("User with this id does not exist");
            } else {
                users.put(user.getId(), user);
                log.info("User with id: " + user.getId() + " successfully updated");
            }
        } else {
            log.info("Fields filled out incorrectly");
        }
        if (user.getFriendsIdsSet() == null) {
            user.setFriendsIdsSet(new HashSet<>());
        }
        return user;
    }

    public Collection<User> findUsers() {
        log.info("Number of users: " + users.size());
        return users.values();
    }

    public User findUser(long userId) {
        if (users.get(userId) != null) {
            log.info("User found");
            return users.get(userId);
        } else {
            throw new NotFoundException("User with this id was not found");
        }
    }


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

    boolean validateUserFields(User user) {
        return validateEmail(user) && validateLogin(user) && validateBirthday(user);
    }
}
