package dev.katha.library.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.katha.library.model.Book;
import dev.katha.library.model.Checkout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;



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

    public Boolean checkoutBookByUser(String userEmail, Long bookId) {
//        Checkout checkoutbook = checkoutRestClient.get().uri("/checkouts/search/findByUserEmailAndBookId?userEmail={userEmail}&bookId={bookId}", userEmail, bookId).accept(MediaType.APPLICATION_JSON).retrieve().body(Checkout.class);
//        ResponseEntity<String> result = checkoutRestClient.get().uri("/checkouts/search/findByUserEmailAndBookId?userEmail={userEmail}&bookId={bookId}", userEmail, bookId).retrieve().toEntity(String.class);
        ResponseEntity<Object> result = checkoutRestClient
                .get()
                .uri("/checkouts/search/findByUserEmailAndBookId?userEmail={userEmail}&bookId={bookId}", userEmail, bookId).accept(MediaTypes.HAL_JSON).retrieve().toEntity(Object.class);

        System.out.println("Response status: " + result.getStatusCode());
        System.out.println("Response headers: " + result.getHeaders());
        System.out.println("Contents: " + result.getBody());


        Object entities = result.getBody();
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) entities;
        LinkedHashMap<String, ArrayList> embedded = (LinkedHashMap<String, ArrayList>) map.get("_embedded");
//        ObjectMapper mapper = new ObjectMapper();
        List checkouts =  embedded.get("checkouts");
        LinkedHashMap<String, Object> checkoutobj = (LinkedHashMap<String, Object>) checkouts.get(0);
        Checkout checkout = new Checkout(((Integer)checkoutobj.get("bookId")).longValue(), checkoutobj.get("userEmail").toString(), checkoutobj.get("checkoutDate").toString(), checkoutobj.get("returnDate").toString());
        checkout.setId(((Integer)checkoutobj.get("id")).longValue());
        if(checkout.getId() != null) {
            return true;
        }
        return false;

    }

    public int currentLoansCount(String userEmail) {
//        return checkoutRepository.findBooksByUserEmail(userEmail).size();
        ResponseEntity<Object> result = checkoutRestClient
                .get()
                .uri("/checkouts/search/countAllByUserEmail?userEmail={userEmail}", userEmail).accept(MediaTypes.HAL_JSON).retrieve().toEntity(Object.class);

        System.out.println("Response status: " + result.getStatusCode());
        System.out.println("Response headers: " + result.getHeaders());
        System.out.println("Contents: " + result.getBody());
        if(result.hasBody()) {
            return Integer.parseInt(result.getBody().toString());
        } else {
            return 0; // TODO: Replace this line with exception handling
        }
    }

//    public List<ShelfCurrentLoansResponse> currentLoans(String userEmail) throws Exception {
//
//        List<ShelfCurrentLoansResponse> shelfCurrentLoansResponses = new ArrayList<>();
//
//        List<Checkout> checkoutList = checkoutRepository.findBooksByUserEmail(userEmail);
//        List<Long> bookIdList = new ArrayList<>();
//
//        for (Checkout i: checkoutList) {
//            bookIdList.add(i.getBookId());
//        }
//
//        List<Book> books = bookRepository.findBooksByBookIds(bookIdList);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        for (Book book : books) {
//            Optional<Checkout> checkout = checkoutList.stream()
//                    .filter(x -> x.getBookId() == book.getId()).findFirst();
//
//            if (checkout.isPresent()) {
//
//                Date d1 = sdf.parse(checkout.get().getReturnDate());
//                Date d2 = sdf.parse(LocalDate.now().toString());
//
//                TimeUnit time = TimeUnit.DAYS;
//
//                long difference_In_Time = time.convert(d1.getTime() - d2.getTime(),
//                        TimeUnit.MILLISECONDS);
//
//                shelfCurrentLoansResponses.add(new ShelfCurrentLoansResponse(book, (int) difference_In_Time));
//            }
//        }
//        return shelfCurrentLoansResponses;
//    }

//    public void returnBook (String userEmail, Long bookId) throws Exception {
//
//        Optional<Book> book = bookRepository.findById(bookId);
//
//        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
//
//        if (!book.isPresent() || validateCheckout == null) {
//            throw new Exception("Book does not exist or not checked out by user");
//        }
//
//        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);
//
//        bookRepository.save(book.get());
//        checkoutRepository.deleteById(validateCheckout.getId());
//
//        History history = new History(
//                userEmail,
//                validateCheckout.getCheckoutDate(),
//                LocalDate.now().toString(),
//                book.get().getTitle(),
//                book.get().getAuthor(),
//                book.get().getDescription(),
//                book.get().getImg()
//        );
//
//        historyRepository.save(history);
//    }
//
//    public void renewLoan(String userEmail, Long bookId) throws Exception {
//
//        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
//
//        if (validateCheckout == null) {
//            throw new Exception("Book does not exist or not checked out by user");
//        }
//
//        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//        Date d1 = sdFormat.parse(validateCheckout.getReturnDate());
//        Date d2 = sdFormat.parse(LocalDate.now().toString());
//
//        if (d1.compareTo(d2) > 0 || d1.compareTo(d2) == 0) {
//            validateCheckout.setReturnDate(LocalDate.now().plusDays(7).toString());
//            checkoutRepository.save(validateCheckout);
//        }
//    }

}
