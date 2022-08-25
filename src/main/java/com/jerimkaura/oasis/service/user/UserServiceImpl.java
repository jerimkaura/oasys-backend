package com.jerimkaura.oasis.service.user;

import com.jerimkaura.oasis.domain.Church;
import com.jerimkaura.oasis.domain.Role;
import com.jerimkaura.oasis.domain.User;
import com.jerimkaura.oasis.repository.RoleRepository;
import com.jerimkaura.oasis.repository.UserRepository;
import com.jerimkaura.oasis.service.s3.FileStore;
import com.jerimkaura.oasis.web.models.dto.UserDto;
import com.jerimkaura.oasis.web.models.requests.UploadProfilePictureDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.apache.http.entity.ContentType.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStore fileStore;

    @Value("${oasis.aws-bucket-name}")
    private String bucketName;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            log.error("User by {} not found", username);
            throw new UsernameNotFoundException("User not found");
        } else {
            log.error("User by {}  found", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));

        return new org
                .springframework
                .security
                .core
                .userdetails
                .User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving {} to database", user.getFirstName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving {} to database", role.getRoleName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {}  to user by username {} ", roleName, username);
        User user = userRepository.findUserByUsername(username);
        Role role = roleRepository.findByRoleName(roleName);
        user.getRoles().add(role);
    }


    @Override
    public User getUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User getUserByUserName(String username) {
        log.info("Getting  user by username {} from database", username);
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching users from database");
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByChurch(Church church) {
        log.info("Fetching users from {} ", church.getName());
        return userRepository.findUserByChurch(church);
    }

    @Override
    public UserDto uploadProfilePicture(UploadProfilePictureDto pictureDto) {
        User user = userRepository.findUserById(pictureDto.getId());
        MultipartFile multipartFile = pictureDto.getProfileImage();

        if (multipartFile.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }

        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(multipartFile.getContentType())) {
            throw new IllegalStateException("File uploaded is not an image");
        }

        String fileName = user.getId() + "-profile-image";
        String imageUrl = fileStore.uploadFile(
                fileName,
                bucketName,
                "profiles",
                multipartFile
        );
        log.error(imageUrl);
        user.setProfileUrl(imageUrl);
        return new ModelMapper().map(userRepository.save(user), UserDto.class);
    }

}
