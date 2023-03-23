package ru.application.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.application.filmorate.model.Film;
import ru.application.filmorate.model.User;
import ru.application.filmorate.storage.FilmStorage;
import ru.application.filmorate.storage.FriendStorage;
import ru.application.filmorate.storage.UserStorage;
import ru.application.filmorate.storage.util.enumeration.EventType;
import ru.application.filmorate.storage.util.enumeration.OperationType;

import java.util.List;


@Service
@AllArgsConstructor
public class UserService {
    private final UserStorage userStorage;
    private final FriendStorage friendStorage;
    private final FilmStorage filmStorage;
    private final EventService eventService;

    /**
     * Метод получения списка всех пользователей
     *
     * @return Список всех пользователей
     */
    public List<User> get() {
        return userStorage.get();
    }

    /**
     * Метод получения пользователя по ID
     *
     * @param userId id пользователя
     * @return Объект пользователя
     */
    public User get(Integer userId) {
        return userStorage.get(userId);
    }

    /**
     * Метод получения общих друзей двух пользователей
     *
     * @param id      id первого пользователя
     * @param otherId id второго пользователя
     * @return Список общих друзей двух пользователей
     */
    public List<User> getListOfFriendsSharedWithAnotherUser(Integer id, Integer otherId) {
        return friendStorage.getListOfFriendsSharedWithAnotherUser(id, otherId);
    }

    /**
     * Метод получения списка друзей пользователя по id
     *
     * @param id id пользователя
     * @return список друзей пользователя
     */
    public List<User> getListOfFriends(Integer id) {
        userStorage.get(id);
        return friendStorage.getListOfFriends(id);
    }

    /**
     * Метод получения списка рекомендованных фильмов для пользователя по ID
     *
     * @param userId id пользователя
     * @return Список фильмов
     */
    public List<Film> getRecommendations(Integer userId) {
        validation(userStorage.get(userId));
        return filmStorage.getRecommendedFilms(userId);
    }

    /**
     * Метод создания пользователя
     *
     * @param user Принятый объект пользователя
     * @return созданный объект пользователя
     */
    public User create(User user) {
        validation(user);
        return userStorage.create(user);
    }

    /**
     * Метод изменения пользователя
     *
     * @param user Принятый объект пользователя
     * @return изменённый объект пользователя
     */
    public User update(User user) {
        validation(user);
        return userStorage.update(user);
    }

    /**
     * Метод добавления пользователя в друзья
     *
     * @param id       id первого пользователя
     * @param friendId id второго пользователя
     */
    public void addFriends(Integer id, Integer friendId) {
        friendStorage.addFriends(id, friendId);
        eventService.create(id, EventType.FRIEND, OperationType.ADD, friendId);
    }

    /**
     * Метод удаления пользователя из друзей
     *
     * @param id       id первого пользователя
     * @param friendId id второго пользователя
     */
    public void removeFriends(Integer id, Integer friendId) {
        friendStorage.removeFriends(id, friendId);
        eventService.create(id, EventType.FRIEND, OperationType.REMOVE, friendId);
    }

    /**
     * Метод удаления пользователя по ID
     *
     * @param id id пользователя
     */
    public void remove(Integer id) {
        userStorage.remove(id);
    }

    /**
     * Метод проверки поля пользователя на "ничто"
     *
     * @param user Объект пользователя
     */
    private void validation(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}


