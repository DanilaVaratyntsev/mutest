package den.vor.spring.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class BookControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private BookService bookService;

    @Test
    void testGetNextSong() {
        Book book = new Book(1, "Name", 2);
        when(bookService.findById(1)).thenReturn(book);
        // when
        client.get().uri("/books/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(book.id())
                .jsonPath("name").isEqualTo(book.name())
                .jsonPath("copies").isEqualTo(book.copies());
    }
}
