package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class CandidateStore {


    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private final AtomicInteger id = new AtomicInteger();

    private CandidateStore() {
        candidates.put(1, new Candidate(id.incrementAndGet(), "Junior Java Candidate", "Молчу, но все понимаю", LocalDate.now()));
        candidates.put(2, new Candidate(id.incrementAndGet(), "Middle Java Candidate", "Много делаю и молчу", LocalDate.now()));
        candidates.put(3, new Candidate(id.incrementAndGet(), "Senior Java Candidate", "Много говорю, но руками ничего не делаю", LocalDate.now()));
    }


    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public Candidate add(Candidate candidate) {
        candidate.setId(id.incrementAndGet());
        candidate.setCreated(LocalDate.now());
        return candidates.put(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public Candidate update(Candidate candidate) {
        return candidates.replace(candidate.getId(), candidate);
    }
}
