package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.jdbc.Sql;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.config.JdbcConfiguration;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PostDBStoreTest {

    @BeforeEach
    void clean() throws SQLException {
        new JdbcConfiguration().loadPool().
                getConnection().prepareStatement("TRUNCATE TABLE post RESTART IDENTITY").execute();
    }

    @Ignore
    @Test
    public void whenFindAllPosts() {
        PostDBStore store = new PostDBStore(new JdbcConfiguration().loadPool());
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
        PostDBStore store = new PostDBStore(new JdbcConfiguration().loadPool());
        Post post = new Post(2, "Java Job", "test", LocalDate.now(), new City(1));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenUpdatePost() {
        PostDBStore store = new PostDBStore(new JdbcConfiguration().loadPool());
        Post post = new Post(3, "Java Job", "test", LocalDate.now(), new City(1));
        store.add(post);
        post.setName("Java senior job");
        store.update(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }
}