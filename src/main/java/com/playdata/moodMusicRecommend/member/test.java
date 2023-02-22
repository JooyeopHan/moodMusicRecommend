package com.playdata.moodMusicRecommend.member;

import com.playdata.moodMusicRecommend.ResultDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/recommend")
public class test {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService service;

    @PostMapping("/list")
    public ResponseEntity<ResultDTO> testing(@RequestBody String test,@AuthenticationPrincipal User user){

        ResultDTO dto = new ResultDTO();
        HttpHeaders headers = new HttpHeaders();
        dto.setMsg("성공적으로 저장되었습니다.");
        dto.setRes(false);
        dto.setUrl("/emotion");
        Member presentUser;
        //post JSON.stringfy -> string { 'arousal': } -> '{ \'arousal\' : } <- string
        // request parsing
        JSONObject job = new JSONObject(test);
        JSONArray list = job.getJSONArray("list");
        JSONArray result = job.getJSONArray("result");
        String emotion = result.getString(1);
        String pre_emotion = result.getString(0);
        String nickname = job.getString("nickname");
        Double[] means = new Double[2];
        double result_valence = 0;
        double pre_valence = 0;
        double result_arousal = 0;
        double pre_arousal = 0;

        // DB에서 user 감정 셋을 끌어옴
        Optional<Member> mem = memberRepository.findByNickname(user.getUsername());
        if(mem.isPresent()){
            System.out.println(mem.get().getHappinessV());
            switch (pre_emotion){
                case "anger":
                    pre_valence += mem.get().getAngerV();
                    pre_arousal += mem.get().getAngerA();
                    break;
                case "happiness":
                    pre_valence += mem.get().getHappinessV();
                    pre_arousal += mem.get().getHappinessA();
                    break;
                case "disgust":
                    pre_valence += mem.get().getDisgustV();
                    pre_arousal += mem.get().getDisgustA();
                    break;
                case "fear":
                    pre_valence += mem.get().getFearV();
                    pre_arousal += mem.get().getFearA();
                    break;
                case "neutral":
                    pre_valence += mem.get().getNeutralV();
                    pre_arousal += mem.get().getNeutralA();
                    break;
                case "sadness":
                    pre_valence += mem.get().getSadnessV();
                    pre_arousal += mem.get().getSadnessA();
                    break;
                case "surprise":
                    pre_valence += mem.get().getSurpriseV();
                    pre_arousal += mem.get().getSurpriseA();
                    break;
            }
        }

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
        // DB 에 보낸다.
        presentUser = mem.get();
        switch (emotion){
            case "anger":
                presentUser.setAngerV(means[0]+pre_valence);
                presentUser.setAngerA(means[1]+pre_arousal);
                break;
            case "happiness":
                presentUser.setHappinessV(means[0]+pre_valence);
                presentUser.setHappinessA(means[1]+pre_arousal);
                break;
            case "disgust":
                presentUser.setDisgustV(means[0]+pre_valence);
                presentUser.setDisgustA(means[1]+pre_arousal);
                break;
            case "fear":
                presentUser.setFearV(means[0]+pre_valence);
                presentUser.setFearA(means[1]+pre_arousal);
                break;
            case "neutral":
                presentUser.setNeutralV(means[0]+pre_valence);
                presentUser.setNeutralA(means[1]+pre_arousal);
                break;
            case "sadness":
                presentUser.setSadnessV(means[0]+pre_valence);
                presentUser.setSadnessA(means[1]+pre_arousal);
                break;
            case "surprise":
                presentUser.setSurpriseV(means[0]+pre_valence);
                presentUser.setSurpriseA(means[1]+pre_arousal);
                break;
        }
        service.update(presentUser);
        System.out.println(presentUser.getHappinessV());

        dto.setRes(true);
        return ResponseEntity.accepted().headers(headers).body(dto);
    }
}
