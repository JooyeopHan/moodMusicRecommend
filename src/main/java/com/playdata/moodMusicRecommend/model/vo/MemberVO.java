package com.playdata.moodMusicRecommend.model.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MemberVO {
    private Long id;
    private String nickname;
    private String passwd;

    private String email;
}
