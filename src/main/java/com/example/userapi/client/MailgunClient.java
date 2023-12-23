package com.example.userapi.client;

import com.example.userapi.client.mailgun.SendMailForm;
import feign.Response;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mailgun", url ="https://api.mailgun.net/v3/",configuration = MailgunClient.Configuration.class)
@Qualifier("mailgun")
public interface MailgunClient {
    @PostMapping("sandbox80e61004ba494c36a0f4b2199ec898c9.mailgun.org/messages")
    ResponseEntity<String> sendEmail(@SpringQueryMap SendMailForm form);

    class Configuration {
        @Value("${mailgun-api-key}")
        private String apiKey;

        @Bean
        public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
            return new BasicAuthRequestInterceptor("api", apiKey);
        }
    }
}
