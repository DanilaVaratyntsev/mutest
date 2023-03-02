package den.vor.spring.app;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BookRepository {

    private final Map<Long, Book> bookMap = new ConcurrentHashMap<>();

    public BookRepository() {
        bookMap.put(1L, new Book(1, "Das Glasperlenspiel", 2));
    }

    public Optional<Book> findById(long id) {
        return Optional.ofNullable(bookMap.get(id));
    }

    public void update(Book book) {
        bookMap.put(book.id(), book);
    }

    public void create(Book book) {
        bookMap.put(book.id(), book);
    }
}
