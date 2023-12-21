package com.example.userapi.client.mailgun;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
@Builder
public class SendMailForm {
    private String from;
    private String to;
    private String subject;
    private String text;
}
