package com.help_your_partner.user_service.application.port.out;

public interface PasswordHasher {
    String hash(String password);
    boolean match(String password, String hashed);
}
