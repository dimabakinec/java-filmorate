package ru.yandex.practicum.filmorate.storage.user;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.UserValidation;

import java.util.*;

@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
@Qualifier("inMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    final UserValidation userValidation = new UserValidation();
    long nextId = 1;
    final Map<Long, User> users = new HashMap<>();

    public Map<Long, User> getUsers() {
        return users;
    }

    public User addNewUser(User user) {
        if (user.getName() == null || Objects.equals(user.getName(), "") || Objects.equals(user.getName(), " ")) {
            user.setName(user.getLogin());
        }
        if (userValidation.validateUserFields(user)) {
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
        if (userValidation.validateUserFields(user)) {
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
}
