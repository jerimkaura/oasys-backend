package com.jerimkaura.oasis.service.church;

import com.jerimkaura.oasis.domain.Church;
import com.jerimkaura.oasis.domain.User;
import com.jerimkaura.oasis.repository.ChurchRepository;
import com.jerimkaura.oasis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChurchServiceImpl implements ChurchService {
    private final ChurchRepository churchRepository;
    private final UserRepository userRepository;

    @Override
    public Church saveChurch(Church church) {
        log.info("Saving {} to database", church.getName());
        return churchRepository.save(church);
    }

    @Override
    public void enrollUserToChurch(User user, Long churchId) {
        Church church = churchRepository.findChurchById(churchId);
        user.setChurch(church);
        church.getMembers().add(user);
    }

    @Override
    public Church getChurch(Long id) {
        return churchRepository.findChurchById(id);
    }
}
