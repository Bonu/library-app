package dev.katha.library.checkout.dao;

import dev.katha.library.checkout.entity.Checkout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {


    List<Checkout> findByUserEmailAndBookId(String userEmail, Long bookId);

}