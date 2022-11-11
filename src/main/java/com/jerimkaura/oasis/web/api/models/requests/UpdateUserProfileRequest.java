package com.jerimkaura.oasis.web.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserProfileRequest {
    private  Long id;
    private  String firstName;
    private  String lastName;
    private  String gender;
    private  String phoneNumber;
    private  String location;
}
