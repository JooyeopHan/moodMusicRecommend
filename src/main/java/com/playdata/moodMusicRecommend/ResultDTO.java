package com.playdata.moodMusicRecommend;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
public class ResultDTO {

    private boolean res;

    private String url;

    private String msg;

    private String auth;
}
