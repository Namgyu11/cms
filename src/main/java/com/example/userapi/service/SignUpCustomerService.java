package com.example.userapi.service;

import com.example.userapi.client.MailgunClient;
import com.example.userapi.domain.SignUpForm;
import com.example.userapi.domain.model.Customer;
import com.example.userapi.domain.repository.CustomerRepository;
import com.example.userapi.exception.CustomException;
import com.example.userapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignUpCustomerService {
    private final CustomerRepository customerRepository;



    public Customer signUp(SignUpForm form) {
        // Customer 객체 만들기
        return this.customerRepository.save(Customer.from(form));
    }

    public boolean isEmailExist(String email) {
        return customerRepository.findByEmail(email.toLowerCase(Locale.ROOT)).isPresent();
    }

    // 예외가 발생하면 이메일 인증 과정 자체가 실패해야 되기 때문에 @Transactional 사용
    // -> 데이터의 일관성
    @Transactional
    public void verifyEmail(String email, String code){
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        if(customer.isVerify()){
            throw new CustomException(ErrorCode.ALREADY_VERIFY);
        }
        else if(!customer.getVerificationCode().equals(code)){
            throw new CustomException(ErrorCode.WRONG_VERIFICATION);
        }
        else if(customer.getVerifyExpiredAt().isBefore(LocalDateTime.now())){
            throw new CustomException(ErrorCode.EXPIRE_CODE);
        }
        customer.setVerify(true);
    }

    // 트랜잭션을 걸게 되면 JPA 내부에서 동작하는 것 때문에 save 를 하지 않아도 변경사항을 알아채서 자동으로 저장
    @Transactional
    public LocalDateTime changeCustomerValidateEmail(Long customerId, String verificationCode) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if(customerOptional.isPresent()){
            Customer customer = customerOptional.get();
            customer.setVerificationCode(verificationCode);
            customer.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
            return customer.getVerifyExpiredAt();
        }
        throw new CustomException(ErrorCode.NOT_FOUND_USER);
    }


}
