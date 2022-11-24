package com.jerimkaura.oasis.service.email;

public interface EmailSender {
    void sendEmail(String to, String email, String subject);
}
