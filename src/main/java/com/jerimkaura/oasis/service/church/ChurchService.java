package com.jerimkaura.oasis.service.church;

import com.jerimkaura.oasis.domain.Church;

public interface ChurchService {
    Church saveChurch(Church church);
    void enrollUserToChurch(String username, Long id);
    Church getChurch(Long id);
}
