package com.example.userapi.service;

import com.example.userapi.domain.SignUpForm;
import com.example.userapi.domain.model.Customer;
import com.example.userapi.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {

    private final CustomerRepository customerRepository;

    public Customer signUp(SignUpForm form){
        // Customer 객체 만들기!!
       return this.customerRepository.save(Customer.from(form));
    }
}
