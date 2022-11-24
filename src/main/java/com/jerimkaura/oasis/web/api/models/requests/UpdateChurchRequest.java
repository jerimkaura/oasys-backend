package com.jerimkaura.oasis.web.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateChurchRequest {
    private  Long id;
    private  String name;
    private  String location;
}
