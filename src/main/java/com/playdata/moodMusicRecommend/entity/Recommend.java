package com.playdata.moodMusicRecommend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@SequenceGenerator(
        name = "RECOMMEND_SEQ_GENERATOR",
        sequenceName = "recommend_seq",
        initialValue = 1,
        allocationSize = 1
)
public class Recommend {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECOMMEND_SEQ_GENERATOR")
    private Long id;

    private String username;

//    @Column(unique = true)
    private String audio;
    private Double valence;

    private Double arousal;

    private String time;

    private String emotion;
    private String img;

    private String emotionBefore;

    private String musicName;
    private String artist;
}
