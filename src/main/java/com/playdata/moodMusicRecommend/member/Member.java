package com.playdata.moodMusicRecommend.member;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@DynamicInsert
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nickname;

    @Column
    private String passwd;

    @Column(unique = true)
    private String email;

    @Column
    @ColumnDefault("-1.0061421315665533")
    private Double angerV;

    @Column
    @ColumnDefault("1.003714331121677")
    private Double angerA;

    @Column
    @ColumnDefault("1.2184096834800648")
    private Double happinessV;

    @Column
    @ColumnDefault("0.6273507647955341")
    private Double happinessA;

    @Column
    @ColumnDefault("-0.42291398299832356")
    private Double sadnessV;

    @Column
    @ColumnDefault("-0.41111391156856303")
    private Double sadnessA;

    @Column
    @ColumnDefault("0.11999354539308602")
    private Double surpriseV;

    @Column
    @ColumnDefault("0.8315256620835284")
    private Double surpriseA;

    @Column
    @ColumnDefault("-1.0331532003643735")
    private Double disgustV;

    @Column
    @ColumnDefault("0.16694046865133175")
    private Double disgustA;

    @Column
    @ColumnDefault("-1.073581615128979")
    private Double fearV;

    @Column
    @ColumnDefault("0.534304243458926")
    private Double fearA;

    @Column
    @ColumnDefault("0.0")
    private Double neutralV;

    @Column
    @ColumnDefault("0.0")
    private Double neutralA;

}
