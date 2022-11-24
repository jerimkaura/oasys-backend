package com.jerimkaura.oasis.service.church;

import com.jerimkaura.oasis.domain.Church;
import com.jerimkaura.oasis.domain.User;
import com.jerimkaura.oasis.web.api.models.requests.UpdateChurchRequest;

import java.util.List;
import java.util.Optional;

public interface ChurchService {
    Church saveChurch(Church church);
    Church getChurchById(Long id);
    void enrollUserToChurch(User user, Long churchId);
    List<Church> getChurches();
    Church updateChurch(UpdateChurchRequest updateChurchRequest);
}
