package com.playdata.moodMusicRecommend.recommend;


import com.playdata.moodMusicRecommend.member.Member;
import lombok.RequiredArgsConstructor;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;

import org.python.core.PyString;
import org.springframework.stereotype.Service;
import org.python.util.PythonInterpreter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service
public class RecommendService {

    private final RecommendRepository repository;
    private static PythonInterpreter pinterpreter;
    public Optional<String> recommendMusic(List imageList){
        // Import the mymodule module
//        System.setProperty("python.import.site", "false");
        pinterpreter.exec("import sys");
        pinterpreter.exec("sys.path.append('src/main/')");
        pinterpreter.exec("import pythonAlgo");
//        python.exec("result = pythonAlgo.main.detecting("+imageList.get(0)+","+imageList.get(1)+")");

        pinterpreter = new PythonInterpreter();
//        pinterpreter.exec("import sys");
//        pinterpreter.exec("sys.path.append('../')");
        pinterpreter.execfile("src/main/pythonAlgo/main.py"); // Call the add_squared function in the mymodule module
//        pinterpreter.exec("result = detecting("+imageList.get(0)+","+imageList.get(1)+")"); // Get the result from Pytho
        int result = pinterpreter.get("result", Integer.class);
         System.out.println("Result: " + result);

        PyFunction pyFuntion = (PyFunction) pinterpreter.get("detecting", PyFunction.class);
        PyObject pyobj = pyFuntion.__call__(new PyString((String) imageList.get(0)), new PyString((String) imageList.get(1)));
        System.out.println(pyobj.toString());

        return null;
    }

    public String create(Map<String, Object> likeList) {


        System.out.println("list : " + likeList.get("list"));
        System.out.println("result : " + likeList.get("result"));
//        System.out.println("nickname : " + likeList.get("nickname"));

        ArrayList<?> emotion = (ArrayList<?>) likeList.get("result");
        ArrayList<?> musicList = (ArrayList<?>) likeList.get("list");

        System.out.println(musicList.get(0));
        System.out.println(musicList.get(0).getClass());

        LinkedHashMap firstMusic = (LinkedHashMap) musicList.get(0);

        System.out.println(firstMusic.get("arousal"));
        System.out.println(firstMusic.get("valence"));

//        System.out.println(emotion.get(1));

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
            recommend.setAudio(music.get("audio").toString());
            recommend.setTime(formatedNow);
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
