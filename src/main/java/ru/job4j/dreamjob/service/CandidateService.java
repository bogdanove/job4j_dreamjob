package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.persistence.CandidateDbStore;

import java.util.Collection;
import java.util.List;

@ThreadSafe
@Service
public class CandidateService {

    private final CandidateDbStore candidateStore;
    private final CityService cityService;


    public CandidateService(CandidateDbStore candidateStore, CityService cityService) {
        this.candidateStore = candidateStore;
        this.cityService = cityService;
    }


    public Collection<Candidate> findAll() {
        List<Candidate> candidates = candidateStore.findAll();
        candidates.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId())
                )
        );
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        return candidateStore.add(candidate);
    }

    public Candidate findById(int id) {
        Candidate candidate = candidateStore.findById(id);
        candidate.setCity(cityService.findById(candidate.getCity().getId()));
        return candidate;
    }

    public void update(Candidate candidate) {
        candidateStore.update(candidate);
    }
}
