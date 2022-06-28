package com.example.bookmyshow.data;

import com.example.bookmyshow.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}