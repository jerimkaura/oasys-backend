package com.jerimkaura.oasis.web.api.models.dto;

import com.jerimkaura.oasis.domain.Song;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleUserDto implements Serializable {
    private  Long id;
    private  String firstName;
    private  String lastName;
    private  String username;
    private String profileUrl;
    private String gender;
    private String phoneNumber;
    private String location;
    private Collection<RoleDto> roles;
    private Set<Song> songs = new HashSet<>();
    private ChurchDto church;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoleDto implements Serializable {
        private  Long id;
        private  String roleName;
    }
}
