package com.playdata.moodMusicRecommend.model.vo;

import lombok.Setter;
import lombok.Getter;

import java.util.Date;

@Getter
@Setter
public class MusicVO {
    private String img;
    private Double valence;

    private Double arousal;

    private Date artist;

    private String audio;
    private String track;
}
