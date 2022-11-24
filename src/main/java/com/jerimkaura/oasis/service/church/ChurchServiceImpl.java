package com.jerimkaura.oasis.service.church;

import com.jerimkaura.oasis.domain.Church;
import com.jerimkaura.oasis.domain.User;
import com.jerimkaura.oasis.repository.ChurchRepository;
import com.jerimkaura.oasis.repository.UserRepository;
import com.jerimkaura.oasis.web.api.models.requests.UpdateChurchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChurchServiceImpl implements ChurchService {
    private final ChurchRepository churchRepository;
    private final UserRepository userRepository;

    @Override
    public Church saveChurch(Church church) {
        return churchRepository.save(church);
    }

    @Override
    public Church getChurchById(Long id) {
        return churchRepository.findChurchById(id);
    }

    @Override
    public void enrollUserToChurch(User user, Long churchId) {
        Church church = churchRepository.findChurchById(churchId);
        user.setChurch(church);
        church.getMembers().add(user);
    }

    @Override
    public List<Church> getChurches() {
        return churchRepository.findAll();
    }

    @Override
    public Church updateChurch(UpdateChurchRequest updateChurchRequest) {
        Long churchId = updateChurchRequest.getId();
        Church church = churchRepository.findChurchById(churchId);

        String name = updateChurchRequest.getName();
        String location = updateChurchRequest.getLocation();

        if (name != null){
            church.setName(name);
        }

        if (location !=null){
            church.setLocation(location);
        }
        return churchRepository.save(church);
    }
}
