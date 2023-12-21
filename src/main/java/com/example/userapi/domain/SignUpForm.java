package com.example.userapi.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpForm {
    private String email;
    private String name;
    private String password;
    private LocalDate birth;
    private String phone;
}
