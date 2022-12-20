package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CandidateDbStoreTest {

    @Autowired
    private BasicDataSource basicDataSource;
    private CandidateDbStore store;

    @BeforeEach
    public void clean() {
        store = new CandidateDbStore(basicDataSource);
        store.clean();
    }

    @Test
    public void whenFindAllCandidates() {
        Candidate candidate = new Candidate(0, "Java Junior", "test", LocalDate.now(), new City(1), null);
        Candidate candidate1 = new Candidate(1, "Java middle", "test", LocalDate.now(), new City(2), null);
        List<Candidate> posts = Arrays.asList(candidate, candidate1);
        store.add(candidate);
        store.add(candidate1);
        List<Candidate> candidatesInDb = store.findAll();
        assertThat(candidatesInDb).isEqualTo(posts);
    }

    @Test
    public void whenCreateCandidate() {
        Candidate candidate = new Candidate(2, "Java Junior", "test", LocalDate.now(), new City(1), null);
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName()).isEqualTo(candidate.getName());
    }

    @Test
    public void whenUpdateCandidate() {
        Candidate candidate = new Candidate(3, "Java Junior", "test", LocalDate.now(), new City(1), null);
        store.add(candidate);
        candidate.setName("Java Junior candidate");
        store.update(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName()).isEqualTo(candidate.getName());
    }
}