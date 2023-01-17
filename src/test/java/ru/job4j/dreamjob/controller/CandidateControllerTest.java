package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.service.CandidateService;
import ru.job4j.dreamjob.service.CityService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CandidateControllerTest {

    CandidateService candidateService;

    CityService cityService;

    CandidateController candidateController;

    HttpSession session;

    MultipartFile testFile;

    @BeforeEach
    void initServices() {
        candidateService = mock(CandidateService.class);
        cityService = mock(CityService.class);
        session = mock(HttpSession.class);
        candidateController = new CandidateController(candidateService, cityService);
        testFile = new MockMultipartFile("testFile.img", new byte[]{1, 2, 3});
    }

    @Test
    void whenRequestVacancyListPageThenGetPageWithVacancies() {
        var candidate = new Candidate(1, "test1", "desc1", LocalDate.now());
        var candidate1 = new Candidate(2, "test2", "desc2", LocalDate.now());
        var candidateList = List.of(candidate, candidate1);
        when(candidateService.findAll()).thenReturn(candidateList);
        var model = new ConcurrentModel();
        var view = candidateController.candidates(model, session);
        var actualVacancies = model.getAttribute("candidates");
        assertThat(view).isEqualTo("candidates");
        assertThat(actualVacancies).isEqualTo(candidateList);
    }

    @Test
    void whenRequestVacancyCreationPageThenGetPageWithCities() {
        var city1 = new City(1, "Москва");
        var city2 = new City(2, "Санкт-Петербург");
        var expectedCities = List.of(city1, city2);
        when(cityService.getAllCities()).thenReturn(expectedCities);
        var model = new ConcurrentModel();
        var view = candidateController.addCandidate(model, session);
        var actualVacancies = model.getAttribute("cities");
        assertThat(view).isEqualTo("addCandidate");
        assertThat(actualVacancies).isEqualTo(expectedCities);
    }

    @Test
    void whenPostCandidateThenSameDataAndRedirectToVacanciesPage() throws IOException {
        var candidate = new Candidate(1, "test1", "desc1", LocalDate.now(), new City(1), testFile.getBytes());
        var candidateArgumentCaptor = ArgumentCaptor.forClass(Candidate.class);
        when(candidateService.add(candidateArgumentCaptor.capture())).thenReturn(candidate);
        var view = candidateController.createCandidate(candidate, testFile);
        var actualVacancy = candidateArgumentCaptor.getValue();
        assertThat(view).isEqualTo("redirect:/candidates");
        assertThat(actualVacancy).isEqualTo(candidate);
    }

    @Test
    void whenUpdateVacancyAndRedirectToVacanciesPage() throws IOException {
        var candidate = new Candidate(1, "test1", "desc1", LocalDate.now(), new City(1), testFile.getBytes());
        var view = candidateController.updateCandidate(candidate, testFile);
        assertThat(view).isEqualTo("redirect:/candidates");
    }

}