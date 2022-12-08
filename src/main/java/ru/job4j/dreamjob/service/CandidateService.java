package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.persistence.CandidateStore;

import java.util.Collection;

@Service
public class CandidateService {

    private final CandidateStore candidateStore;


    public CandidateService(CandidateStore candidateStore) {
        this.candidateStore = candidateStore;
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
