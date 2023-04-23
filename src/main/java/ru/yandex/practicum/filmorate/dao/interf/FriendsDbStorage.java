package ru.yandex.practicum.filmorate.dao.interf;

import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

public interface FriendsDbStorage {
    void makeFriend(User user, User friend);
    @SuppressWarnings("checkstyle:EmptyLineSeparator")
    void deleteFriend(User user, User friend);
    @SuppressWarnings("checkstyle:EmptyLineSeparator")
    List<User> getFriends(long userId);
    @SuppressWarnings("checkstyle:EmptyLineSeparator")
    List<User> getMutualFriends(long userId, long friendId);
}
