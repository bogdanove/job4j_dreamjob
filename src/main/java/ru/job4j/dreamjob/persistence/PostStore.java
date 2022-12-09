package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class PostStore {


    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final AtomicInteger id = new AtomicInteger();

    private PostStore() {
        posts.put(1, new Post(id.incrementAndGet(), "Junior Java Job", "Ты научишься использовать инструмент сборки Maven. "
                + "Будешь писать модульные тесты и оформлять свой код", LocalDate.now()));
        posts.put(2, new Post(id.incrementAndGet(), "Middle Java Job", "В этом уровне ты создашь парсер вакансий популярного сайта", LocalDate.now()));
        posts.put(3, new Post(id.incrementAndGet(), "Senior Java Job", "Ты научишься использовать Java фреймворки, "
                + "которые используют профессионалы каждый день", LocalDate.now()));
    }


    public Collection<Post> findAll() {
        return posts.values();
    }

    public Post add(Post post) {
        post.setId(id.incrementAndGet());
        post.setCreated(LocalDate.now());
        return posts.put(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public Post update(Post post) {
        return posts.replace(post.getId(), post);
    }
}
