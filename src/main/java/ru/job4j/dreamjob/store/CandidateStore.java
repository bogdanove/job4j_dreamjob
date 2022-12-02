package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Junior Java Candidate", "Молчу, но все понимаю", LocalDate.now()));
        candidates.put(2, new Candidate(2, "Middle Java Candidate", "Много делаю и молчу", LocalDate.now()));
        candidates.put(3, new Candidate(3, "Senior Java Candidate", "Много говорю, но руками ничего не делаю", LocalDate.now()));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
