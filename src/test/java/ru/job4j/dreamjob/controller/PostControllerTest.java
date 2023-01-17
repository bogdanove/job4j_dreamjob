package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostControllerTest {

    PostService postService;

    CityService cityService;

    PostController postController;

    HttpSession session;

    @BeforeEach
    void initServices() {
        postService = mock(PostService.class);
        cityService = mock(CityService.class);
        session = mock(HttpSession.class);
        postController = new PostController(postService, cityService);
    }

    @Test
    void whenRequestVacancyListPageThenGetPageWithVacancies() {
        var vacancy1 = new Post(1, "test1", "desc1", LocalDate.now());
        var vacancy2 = new Post(2, "test2", "desc2", LocalDate.now());
        var expectedVacancies = List.of(vacancy1, vacancy2);
        when(postService.findAll()).thenReturn(expectedVacancies);

        var model = new ConcurrentModel();
        var view = postController.posts(model, session);
        var actualVacancies = model.getAttribute("posts");

        assertThat(view).isEqualTo("posts");
        assertThat(actualVacancies).isEqualTo(expectedVacancies);
    }

    @Test
    void whenRequestVacancyCreationPageThenGetPageWithCities() {
        var city1 = new City(1, "Москва");
        var city2 = new City(2, "Санкт-Петербург");
        var expectedCities = List.of(city1, city2);
        when(cityService.getAllCities()).thenReturn(expectedCities);
        var model = new ConcurrentModel();
        var view = postController.formAddPost(model, session);
        var actualVacancies = model.getAttribute("cities");
        assertThat(view).isEqualTo("addPost");
        assertThat(actualVacancies).isEqualTo(expectedCities);
    }

    @Test
    void whenPostVacancyThenSameDataAndRedirectToVacanciesPage() {
        var vacancy = new Post(1, "test1", "desc1", LocalDate.now(), new City(1));
        var vacancyArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        when(postService.add(vacancyArgumentCaptor.capture())).thenReturn(vacancy);
        var view = postController.createPost(vacancy);
        var actualVacancy = vacancyArgumentCaptor.getValue();
        assertThat(view).isEqualTo("redirect:/posts");
        assertThat(actualVacancy).isEqualTo(vacancy);
    }

    @Test
    void whenUpdateVacancyAndRedirectToVacanciesPage() {
        var vacancy = new Post(1, "test1", "desc1", LocalDate.now(), new City(1));
        var view = postController.updatePost(vacancy);
        assertThat(view).isEqualTo("redirect:/posts");
    }
}