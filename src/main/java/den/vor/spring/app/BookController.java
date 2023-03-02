package den.vor.spring.app;

import den.vor.spring.app.exceptions.BookAlreadyExistsException;
import den.vor.spring.app.exceptions.BookNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    @GetMapping("/{id}")
    public Book getById(@PathVariable long id) {
        return bookService.findById(id);
    }

    @PostMapping
    public void createBook(@RequestBody Book book) {
        bookService.create(book);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    public void exceptionHandler() {
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(BookAlreadyExistsException.class)
    public void conflictHandler() {
    }

}
