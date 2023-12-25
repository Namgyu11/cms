package com.example.userapi.application;

import com.example.userapi.client.MailgunClient;
import com.example.userapi.client.mailgun.SendMailForm;
import com.example.userapi.domain.SignUpForm;
import com.example.userapi.domain.model.Customer;
import com.example.userapi.exception.CustomException;
import com.example.userapi.exception.ErrorCode;
import com.example.userapi.service.SignUpCustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;

    public void customerVerify(String email, String code){
        signUpCustomerService.verifyEmail(email,code);
    }

    public String customerSignUp(SignUpForm form) {
        if (signUpCustomerService.isEmailExist(form.getEmail())) {
            //exception
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);

        } else {
            Customer c = signUpCustomerService.signUp(form);
            LocalDateTime now = LocalDateTime.now();

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("hagaji3@gmail.com")
                    .to(form.getEmail())
                    .subject("Verification Email !!")
                    .text(getVerificationEmailBody(c.getEmail(), c.getName(), code))
                    .build();

            log.info("Send email result : " + mailgunClient.sendEmail(sendMailForm).getBody());
            signUpCustomerService.changeCustomerValidateEmail(c.getId(), code);
            return "회원가입에 성공하였습니다.";
        }

    }

    private String getRandomCode() {
        return RandomStringUtils.random(19, true, true);
    }

    private String getVerificationEmailBody(String email, String name, String code) {

        StringBuilder builder = new StringBuilder();
        return builder.append("Hello").append(name).
                append("! Please Click Link verification.\n\n")
                .append("http://localhost:8082/customer/verify/customer?email=")
                .append(email)
                .append("&code=")
                .append(code).toString();

    }

}
