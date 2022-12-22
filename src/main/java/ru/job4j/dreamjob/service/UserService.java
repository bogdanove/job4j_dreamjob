package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.persistence.UserDBStore;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserDBStore userStore;

    public UserService(UserDBStore userStore) {
        this.userStore = userStore;
    }

    public Collection<User> findAll() {
        return userStore.findAll();
    }

    public Optional<User> add(User user) {
        return userStore.add(user);
    }

    public Optional<User> findById(int id) {
        return userStore.findById(id);
    }

    public void update(User user) {
        userStore.update(user);
    }
}
