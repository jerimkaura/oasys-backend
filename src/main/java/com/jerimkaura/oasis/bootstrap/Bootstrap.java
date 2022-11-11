package com.jerimkaura.oasis.bootstrap;

import com.jerimkaura.oasis.domain.Church;
import com.jerimkaura.oasis.domain.Role;
import com.jerimkaura.oasis.domain.User;
import com.jerimkaura.oasis.service.church.ChurchService;
import com.jerimkaura.oasis.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;


@Component
@AllArgsConstructor
@Slf4j
public class Bootstrap implements CommandLineRunner {
    private UserService userService;
    private ChurchService churchService;

    @Override
    public void run(String... args) {
//        Role roleUser = new Role(null, "ROLE_USER");
//        Role roleSuperAdmin = new Role(null, "ROLE_SUPER_ADMIN");
//        Role roleAdmin = new Role(null, "ROLE_ADMIN");
//
//        Church church1 = new Church(null, "Church 1", "Location 1", new HashSet<>());
//        Church church2 = new Church(null, "Church 2", "Location 2", new HashSet<>());
//
//        userService.saveRole(roleUser);
//        userService.saveRole(roleAdmin);
//        userService.saveRole(roleSuperAdmin);
//
//        User superAdmin = new User(null, "Jerim", "SuperAdmin", "jerimotieno@students.uonbi.ac.ke", null, false, null, null, "1234", new ArrayList<>(), null, new HashSet<>());
//
//        userService.saveUser(superAdmin);
//
//        userService.addRoleToUser(superAdmin.getUsername(), roleUser.getRoleName());
//        userService.addRoleToUser(superAdmin.getUsername(), roleAdmin.getRoleName());
//        userService.addRoleToUser(superAdmin.getUsername(), roleSuperAdmin.getRoleName());
//
//
//        churchService.saveChurch(church1);
//        churchService.saveChurch(church2);
//        churchService.enrollUserToChurch(superAdmin, church1.getId());
//
//        userService.getUsersByChurch(church1);

    }
}
