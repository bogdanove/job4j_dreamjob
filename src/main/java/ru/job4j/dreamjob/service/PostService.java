package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.persistence.PostStore;

import java.util.Collection;

@Service
public class PostService {

    private final PostStore postStore;

    public PostService(PostStore postStore) {
        this.postStore = postStore;
    }


    public Collection<Post> findAll() {
        return postStore.findAll();
    }

    public Post add(Post post) {
        return postStore.add(post);
    }

    public Post findById(int id) {
        return postStore.findById(id);
    }

    public Post update(Post post) {
        return postStore.update(post);
    }
}
