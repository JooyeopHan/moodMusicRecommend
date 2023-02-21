package com.playdata.moodMusicRecommend.member;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recommend")
public class test {

    @PostMapping("/list")
    public void test(@RequestBody String test){
        // DB에서 user 감정 셋을 끌어옴


        JSONObject job = new JSONObject(test);
        JSONArray list = job.getJSONArray("list");
        JSONArray result = job.getJSONArray("result");
        String nickname = job.getString("nickname");
        Double[] means = new Double[2];
        double result_valence = 0;
        double result_arousal = 0;
        System.out.println(list);
        System.out.println(result);
        System.out.println(nickname);
        for(int i = 0; i<list.length();i++){
            JSONObject obj = list.getJSONObject(i);
            double valence = obj.getDouble("valence");
            double arousal = obj.getDouble("arousal");
            result_valence += valence;
            result_arousal += arousal;
        }
        result_valence /= list.length();
        result_arousal /= list.length();

        //happiness 일때 좋아요를 누른 곡의 평균
        means[0] = Math.round(result_valence*10000)/10000.0;
        means[1] = Math.round(result_arousal*10000)/10000.0;
        System.out.println(means[0]);
        System.out.println(means[1]);
    }
}
