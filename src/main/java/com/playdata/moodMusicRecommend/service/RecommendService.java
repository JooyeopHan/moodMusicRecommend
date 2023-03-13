package com.playdata.moodMusicRecommend.service;


import com.playdata.moodMusicRecommend.entity.Recommend;
import com.playdata.moodMusicRecommend.repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import org.python.core.PyFunction;
import org.python.core.PyObject;

import org.python.core.PyString;
import org.springframework.stereotype.Service;
import org.python.util.PythonInterpreter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service
public class RecommendService {

    private final RecommendRepository repository;

    public String create(Map<String, Object> likeList) {


        System.out.println("list : " + likeList.get("list"));
        System.out.println("result : " + likeList.get("result"));

        ArrayList<?> emotion = (ArrayList<?>) likeList.get("result");
        ArrayList<?> musicList = (ArrayList<?>) likeList.get("list");

        System.out.println(musicList.get(0));
        System.out.println(musicList.get(0).getClass());

        LinkedHashMap firstMusic = (LinkedHashMap) musicList.get(0);

        System.out.println(firstMusic.get("arousal"));
        System.out.println(firstMusic.get("valence"));
        int iter;
        System.out.println("music size : " + musicList.size());


        for (iter = 0 ; iter < musicList.size(); iter++){
            System.out.println(musicList.get(iter));
            Recommend recommend = new Recommend();


            LinkedHashMap music = (LinkedHashMap) musicList.get(iter);
            System.out.println("audio" + music.get("audio").toString());
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
            String formatedNow = now.format(formatter);

//            recommend.setId(null);
            recommend.setArousal((Double)music.get("arousal"));
            recommend.setValence((Double)music.get("valence"));
            recommend.setUsername(likeList.get("nickname").toString());
            recommend.setEmotion(emotion.get(1).toString());
            recommend.setEmotionBefore(emotion.get(0).toString());
            if(music.get("audio") != null) recommend.setAudio(music.get("audio").toString());;
            recommend.setTime(formatedNow);
            recommend.setImg(music.get("img").toString());
            recommend.setMusicName(music.get("track").toString());
            recommend.setArtist(music.get("artist").toString());

            this.repository.save(recommend);
        }


        return "done";
    }

    public List<Recommend> select(String username){
        List<Recommend> musicList =  repository.findByUsername(username);

        return musicList;
    }


}
