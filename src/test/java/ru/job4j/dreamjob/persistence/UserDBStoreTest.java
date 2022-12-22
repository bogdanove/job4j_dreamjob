package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.dreamjob.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserDBStoreTest {

    @Autowired
    private BasicDataSource basicDataSource;

    private UserDBStore store;

    @BeforeEach
    public void clean() {
        store = new UserDBStore(basicDataSource);
        store.clean();
    }

    @Test
    public void whenFindAllUsers() {
        User user = new User(0, "1@mail.ru", "test");
        User user1 = new User(1, "2@mail.ru", "test");
        List<User> users = Arrays.asList(user, user1);
        store.add(user);
        store.add(user1);
        List<User> usersInDb = store.findAll();
        assertThat(usersInDb).isEqualTo(users);
    }

    @Test
    public void whenCreateUser() {
        User user = new User(2, "2@mail.ru", "test");
        store.add(user);
        Optional<User> userInDb = store.findById(user.getId());
        assertThat(userInDb.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void whenUpdateUser() {
        User user = new User(3, "3@mail.ru", "test");
        store.add(user);
        user.setEmail("0@mail.ru");
        store.update(user);
        Optional<User> userInDb = store.findById(user.getId());
        assertThat(userInDb.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void whenCreateUserAndUserAlreadySaved() {
        User user = new User(3, "2@mail.ru", "test");
        User user1 = new User(4, "2@mail.ru", "test");
        store.add(user);
        Optional<User> userInDb = store.add(user1);
        assertThat(userInDb).isEmpty();
    }

    @Test
    public void whenFindUserByUsernameAndPassword() {
        User user = new User(3, "2@mail.ru", "test");
        store.add(user);
        Optional<User> userInDb = store.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
        assertThat(userInDb.get()).isEqualTo(user);
    }

    @Test
    public void whenInputIncorrectPassword() {
        User user = new User(3, "2@mail.ru", "test");
        store.add(user);
        Optional<User> userInDb = store.findUserByEmailAndPassword(user.getEmail(), "tost");
        assertThat(userInDb.isEmpty()).isTrue();
    }

    @Test
    public void whenInputIncorrectEmail() {
        User user = new User(3, "2@mail.ru", "test");
        store.add(user);
        Optional<User> userInDb = store.findUserByEmailAndPassword("3@mail.ru", user.getPassword());
        assertThat(userInDb.isEmpty()).isTrue();
    }
}