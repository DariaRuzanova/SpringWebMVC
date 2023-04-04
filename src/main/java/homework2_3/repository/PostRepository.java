package homework2_3.repository;

import homework2_3.exception.NotFoundException;
import homework2_3.model.Post;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
@Repository
public class PostRepository {
    private final Map<Long, Post> listPosts = new ConcurrentHashMap<>();
    private final AtomicLong ID = new AtomicLong();

    public List<Post> all() {
        if (listPosts.isEmpty()) {
            return Collections.emptyList();
        } else {
            return listPosts.values().stream().toList();
        }
    }

    public Optional<Post> getById(long id) {
        Post post = listPosts.getOrDefault(id, null);
        return Optional.ofNullable(post);
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(ID.incrementAndGet());
            listPosts.put(post.getId(), post);
        }else{
            if (listPosts.containsKey(post.getId())){
                listPosts.put(post.getId(), post);
            }
            else {
                throw new NotFoundException("Сохранение поста не возможно. Пост с id: " + post.getId() + " не найден.");
            }
        }
        return post;
    }

    public void removeById(long id) {
        Post item = listPosts.remove(id);
        if (item == null) {
            throw new NotFoundException("Удаление поста не возможно. Пост с id: " + id + " не найден.");
        }
    }
}
