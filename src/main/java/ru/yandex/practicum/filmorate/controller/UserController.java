package ru.yandex.practicum.filmorate.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.TypeOperations;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {
    final UserService userService;
    final String pathForAddOrDeleteFriends = "/{id}/friends/{friendId}";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    User addNewUser(@RequestBody User user) {
        log.info("POST request received to add user");
        return userService.userStorage.addNewUser(user);
    }

    @DeleteMapping(pathForAddOrDeleteFriends)
    User deleteFromFriend(@PathVariable("id") long firstUserId, @PathVariable("friendId") long secondUserId) {
        log.info("Received a DELETE request to unfriend a user" + firstUserId + " and " + secondUserId);
        return userService.addOrDeleteToFriends(firstUserId, secondUserId, TypeOperations.DELETE.toString());
    }

    @PutMapping
    User updateUser(@RequestBody User user) {
        return userService.userStorage.updateUser(user);
    }

    @PutMapping(pathForAddOrDeleteFriends)
    User addToFriend(@PathVariable("id") long firstUserId, @PathVariable("friendId") long secondUserId) {
        log.info("Received PUT friend request from user" + firstUserId + " and " + secondUserId);
        return userService.addOrDeleteToFriends(firstUserId, secondUserId, TypeOperations.ADD.toString());
    }

    @GetMapping
    Collection<User> findUsers() {
        log.info("GET request received to get information about all users");
        return userService.userStorage.findUsers();
    }

    @GetMapping("/{id}")
    User findUser(@PathVariable("id") long userId) {
        log.info("GET request received for user information" + userId);
        return userService.userStorage.findUser(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    List<User> getMutualFriends(@PathVariable("id") long firstUserId, @PathVariable("otherId") long secondUserId) {
        log.info("A GET request received to get a list of mutual friends from users " + firstUserId + " and " + secondUserId);
        return userService.getMutualFriends(firstUserId, secondUserId);
    }

    @GetMapping("/{id}/friends")
    List<User> getFriendsSet(@PathVariable("id") long userId) {
        log.info("A GET request was received to get the list of friends from the user " + userId);
        return userService.getFriendsSet(userId);
    }
}
