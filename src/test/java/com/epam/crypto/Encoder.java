package com.epam.crypto;

import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class Encoder {

    public static void main(String[] args) {

        StandardPasswordEncoder encoder = new StandardPasswordEncoder();
        String pwdAdmin = encoder.encode("admin");
        String pwdTom = encoder.encode("tom");

        System.out.println(pwdAdmin);
        System.out.println(pwdTom);

    }
}
