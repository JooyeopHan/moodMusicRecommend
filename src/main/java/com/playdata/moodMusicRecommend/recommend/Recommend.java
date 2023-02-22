package com.playdata.moodMusicRecommend.recommend;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@DynamicInsert
public class Recommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true)
    private String audio;
    private Double valence;

    private Double arousal;

    private String time;

    private String emotion;
}
