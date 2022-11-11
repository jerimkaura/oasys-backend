package com.jerimkaura.oasis.web.api.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongDto implements Serializable {
    private  Long id;
    private  String title;
    private  String releaseYear;
    private  String audioUrl;
    private  UserDto artist;
}
