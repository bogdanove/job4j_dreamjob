package ru.job4j.dreamjob.persistence;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.dreamjob.config.JdbcConfiguration;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CandidateDbStoreTest {

    CandidateDbStore store;

    @Before
    public void clean() {
        store = new CandidateDbStore(new JdbcConfiguration().loadPool());
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
        assertThat(candidatesInDb, is(posts));
    }

    @Test
    public void whenCreateCandidate() {
        Candidate candidate = new Candidate(2, "Java Junior", "test", LocalDate.now(), new City(1), null);
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenUpdateCandidate() {
        Candidate candidate = new Candidate(3, "Java Junior", "test", LocalDate.now(), new City(1), null);
        store.add(candidate);
        candidate.setName("Java Junior candidate");
        store.update(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
    }
}