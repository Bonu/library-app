package dev.katha.library.service;

import dev.katha.library.model.Book;
import dev.katha.library.model.Checkout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BookService {

    private final RestClient bookRestClient;
    private final RestClient checkoutRestClient;

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    public BookService() {
        bookRestClient = RestClient.builder().baseUrl("http://localhost:8080/api").build();
        checkoutRestClient = RestClient.builder().baseUrl("http://localhost:8082/api").build();
    }

    public Book checkoutBook(String userEmail, Long bookId) throws Exception {
        // Get book by BookId
        Optional<Book> book = Optional.ofNullable(bookRestClient.get().uri("/books/{bookId}",bookId).retrieve().body(Book.class));

        // Get checkout details using user email and book Id
        Optional<Checkout> validateCheckout = Optional.ofNullable(checkoutRestClient.get().uri("/checkouts/search/findByUserEmailAndBookId?userEmail=janardhanbonu@gmail.com&bookId=1").retrieve().body(Checkout.class));

        if (!book.isPresent() || validateCheckout.get().getId()!=null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("Book doesn't exist or already checked out by user");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
//        bookRepository.save(book.get());
        bookRestClient.put()
            .uri("/books/{bookId}", bookId)
            .contentType(MediaType.APPLICATION_JSON)
            .body(book)
            .retrieve()
            .body(Book.class);

        Checkout checkout = new Checkout(
            book.get().getId(),
            userEmail,
            LocalDate.now().toString(),
            LocalDate.now().plusDays(7).toString());
//        checkoutRepository.save(checkout);

        checkoutRestClient.post()
            .uri("/checkouts")
            .contentType(MediaType.APPLICATION_JSON)
            .body(checkout)
            .retrieve();

        log.info("Completed reserving the book");
        return book.get();

    }

}
