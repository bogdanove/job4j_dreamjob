package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.persistence.CandidateStore;

import java.util.Collection;

public class CandidateService {

    private final CandidateStore candidateStore = CandidateStore.instOf();

    private static final CandidateService INST = new CandidateService();

    public static CandidateService instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidateStore.findAll();
    }

    public Candidate add(Candidate candidate) {
        return candidateStore.add(candidate);
    }

    public Candidate findById(int id) {
        return candidateStore.findById(id);
    }

    public Candidate update(Candidate candidate) {
        return candidateStore.update(candidate);
    }
}
