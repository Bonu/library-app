package dev.katha.library.controller;

import dev.katha.library.model.Book;
import dev.katha.library.service.BookService;
import dev.katha.library.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;


    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.checkoutBook(userEmail, bookId);
    }

    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount(@RequestHeader(value = "Authorization") String token) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.currentLoansCount(userEmail);
    }

    @GetMapping("/secure/ischeckedout/byuser")
    public Boolean checkoutBookByUser(@RequestHeader(value="Authorization") String token, @RequestParam Long bookId) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.checkoutBookByUser(userEmail, bookId);
    }
//
//    @PutMapping("/secure/checkout")
//    public Book checkoutBook (@RequestHeader(value = "Authorization") String token,
//                              @RequestParam Long bookId) throws Exception {
////        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
//        String userEmail = "jbonu25@gmail.com";
//        return bookService.checkoutBook(userEmail, bookId);
//    }
//
//    @PutMapping("/secure/return")
//    public void returnBook(@RequestHeader(value = "Authorization") String token,
//                           @RequestParam Long bookId) throws Exception {
////        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
//        String userEmail = "jbonu25@gmail.com";
//        bookService.returnBook(userEmail, bookId);
//    }
//
//    @PutMapping("/secure/renew/loan")
//    public void renewLoan(@RequestHeader(value = "Authorization") String token,
//                          @RequestParam Long bookId) throws Exception {
////        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
//        String userEmail = "jbonu25@gmail.com";
//        bookService.renewLoan(userEmail, bookId);
//    }


}
