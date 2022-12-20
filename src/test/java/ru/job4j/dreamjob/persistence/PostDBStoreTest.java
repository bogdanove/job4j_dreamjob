package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.config.JdbcConfiguration;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class PostDBStoreTest {

    private final PostDBStore store = new PostDBStore(new JdbcConfiguration().loadPool());

    @BeforeEach
    public void clean() {
        store.clean();
    }

    @Test
    public void whenFindAllPosts() {
        Post post = new Post(0, "Java Job", "test", LocalDate.now(), new City(1));
        Post post1 = new Post(1, "Java junior Job", "test", LocalDate.now(), new City(2));
        List<Post> posts = Arrays.asList(post, post1);
        store.add(post);
        store.add(post1);
        List<Post> postsInDb = store.findAll();
        assertThat(postsInDb).isEqualTo(posts);
    }

    @Test
    public void whenCreatePost() {
        Post post = new Post(2, "Java Job", "test", LocalDate.now(), new City(1));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    public void whenUpdatePost() {
        Post post = new Post(3, "Java Job", "test", LocalDate.now(), new City(1));
        store.add(post);
        post.setName("Java senior job");
        store.update(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }
}