package den.vor.spring.app;

import den.vor.spring.app.exceptions.BookAlreadyExistsException;
import den.vor.spring.app.exceptions.BookNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book findById(long id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    public void create(Book book) {
        Optional<Book> byId = bookRepository.findById(book.id());
        if (byId.isEmpty()) {
            bookRepository.create(book);
        } else {
            throw new BookAlreadyExistsException();
        }
    }

    public boolean reserve(long id) {
        Optional<Book> byId = bookRepository.findById(id);
        if (byId.isEmpty()) {
            return false;
        }
        Book book = byId.get();
        if (book.copies() <= 0) {
            return false;
        }
        bookRepository.update(new Book(book.id(), book.name(), book.copies() - 1));
        return true;
    }

}
