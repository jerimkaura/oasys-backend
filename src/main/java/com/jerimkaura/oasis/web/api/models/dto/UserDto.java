package com.jerimkaura.oasis.web.api.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private  Long id;
    private  String firstName;
    private  String lastName;
    private  String username;
    private String profileUrl;
    private String gender;
    private String phoneNumber;
    private String location;
    private ChurchDto church;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoleDto implements Serializable {
        private  Long id;
        private  String roleName;
    }
}
