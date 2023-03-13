package com.playdata.moodMusicRecommend.model.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImgDTO{

    private MultipartFile imgBefore;
    private MultipartFile imgAfter;


}
