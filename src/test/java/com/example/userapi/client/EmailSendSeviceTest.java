package com.example.userapi.client;

import com.example.userapi.client.MailgunClient;
import com.example.userapi.config.FeignConfig;
import feign.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailSendServiceTest {
    @Autowired
    private MailgunClient mailgunClient;

    @Test
    public void EmailTest () {
        //숙제
        mailgunClient.sendEmail(null);
        //given
        //when
        //then
//        String response = emailSendService.sendEmail();
//
//        System.out.println(response);
    }

}