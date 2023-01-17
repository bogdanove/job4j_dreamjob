package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    UserService userService;

    UserController userController;

    @BeforeEach
    void initServices() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    void whenUserRegistrationSuccessfully() {
        var user = Optional.of(new User(1, "name", "mail@mail.ru", "password"));
        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.add(userArgumentCaptor.capture())).thenReturn(user);
        var model = new ConcurrentModel();
        var view = userController.registration(model, user.get());
        var actualUser = userArgumentCaptor.getValue();
        assertThat(view).isEqualTo("redirect:/success");
        assertThat(actualUser).isEqualTo(user.get());
    }

    @Test
    void whenUserRegistrationFailed() {
        var user = Optional.of(new User(1, "name", "mail@mail.ru", "password"));
        Optional<User> user1 = Optional.empty();
        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.add(userArgumentCaptor.capture())).thenReturn(user1);
        var model = new ConcurrentModel();
        var view = userController.registration(model, user.get());
        var actualUser = userArgumentCaptor.getValue();
        assertThat(view).isEqualTo("redirect:/fail");
        assertThat(actualUser).isEqualTo(user.get());
    }
}