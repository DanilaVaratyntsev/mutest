package den.vor.spring.app;

import den.vor.spring.app.exceptions.BookAlreadyExistsException;
import den.vor.spring.app.exceptions.BookNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void shouldReturnFoundBook() {
        int id = 2;
        Book book = new Book(id, "Name", 1);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        Book actual = bookService.findById(id);

        assertEquals(book, actual);
        verify(bookRepository, only()).findById(id);
    }

    @Test
    public void shouldThrowExceptionWhenSearchingForNonExistingBook() {
        int id = 2;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.findById(id));

        verify(bookRepository, only()).findById(id);
    }

    @Test
    public void shouldCreateNonExistingBook() {
        int id = 2;
        Book book = new Book(id, "Name", 1);
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        bookService.create(book);

        verify(bookRepository).findById(id);

        // Comment this line to see mutant survived
        verify(bookRepository).create(book);
    }

    @Test
    public void shouldThrowExceptionWhenCreatingExistingBook() {
        int id = 2;
        Book book = new Book(id, "Name", 1);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        assertThrows(BookAlreadyExistsException.class, () -> bookService.create(book));

        verify(bookRepository, only()).findById(id);
    }

    // Reserve method tests. Comment out any to see mutants survive

    @Test
    public void shouldReturnFalseWhenReservingNonExistingBook() {
        int id = 2;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        boolean reserve = bookService.reserve(id);
        assertFalse(reserve);

        verify(bookRepository, only()).findById(id);
    }

    @Test
    public void shouldReturnFalseWhenReservingBookWith0Copies() {
        int id = 2;
        Book book = new Book(id, "Name", 0);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        boolean reserve = bookService.reserve(id);
        assertFalse(reserve);

        verify(bookRepository, only()).findById(id);
    }

    @Test
    public void shouldReserveAvailableBook() {
        int id = 2;
        Book book = new Book(id, "Name", 1);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        boolean reserve = bookService.reserve(id);
        assertTrue(reserve);

        verify(bookRepository).findById(id);
        verify(bookRepository).update(new Book(id, book.name(), 0));
    }
}
