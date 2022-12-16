package ru.job4j.dreamjob.persistence;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.job4j.dreamjob.config.JdbcConfiguration;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PostDBStoreTest {

    PostDBStore store;

    @Before
    public void clean() {
        store = new PostDBStore(new JdbcConfiguration().loadPool());
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
        assertThat(postsInDb, is(posts));
    }

    @Test
    public void whenCreatePost() {
        Post post = new Post(2, "Java Job", "test", LocalDate.now(), new City(1));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenUpdatePost() {
        Post post = new Post(3, "Java Job", "test", LocalDate.now(), new City(1));
        store.add(post);
        post.setName("Java senior job");
        store.update(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }
}