package com.jerimkaura.oasis.service.confirmationtoken;

import com.jerimkaura.oasis.domain.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken confirmationToken);
    Optional<ConfirmationToken> getToken(String token);
    int setConfirmedAt(String token);
}
