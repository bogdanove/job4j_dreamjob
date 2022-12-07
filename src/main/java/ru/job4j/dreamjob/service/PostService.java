package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.persistence.PostStore;

import java.util.Collection;

public class PostService {

    private final PostStore postStore = PostStore.instOf();

    private static final PostService INST = new PostService();

    public static PostService instOf() {
        return INST;
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
