package com.jerimkaura.oasis.service.church;

import com.jerimkaura.oasis.domain.Church;
import com.jerimkaura.oasis.domain.User;

public interface ChurchService {
    Church saveChurch(Church church);
    void enrollUserToChurch(User user, Long churchId);
    Church getChurch(Long id);
}
