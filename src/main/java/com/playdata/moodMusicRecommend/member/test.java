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
        String nickname = job.getString("nickname");
        Double[] means = new Double[2];
        double result_valence = 0;
        double result_arousal = 0;

        // DB에서 user 감정 셋을 끌어옴
        Optional<Member> mem = memberRepository.findByNickname(user.getUsername());
        if(mem.isPresent()){
            System.out.println(mem.get().getHappinessV());
            switch (emotion){
                case "anger":
                    result_valence += mem.get().getAngerV();
                    result_arousal += mem.get().getAngerA();
                    break;
                case "happiness":
                    result_valence += mem.get().getHappinessV();
                    result_arousal += mem.get().getHappinessA();
                    break;
                case "disgust":
                    result_valence += mem.get().getDisgustV();
                    result_arousal += mem.get().getDisgustA();
                    break;
                case "fear":
                    result_valence += mem.get().getFearV();
                    result_arousal += mem.get().getFearA();
                    break;
                case "neutral":
                    result_valence += mem.get().getNeutralV();
                    result_arousal += mem.get().getNeutralA();
                    break;
                case "sadness":
                    result_valence += mem.get().getSadnessV();
                    result_arousal += mem.get().getSadnessA();
                    break;
                case "surprise":
                    result_valence += mem.get().getSurpriseV();
                    result_arousal += mem.get().getSurpriseA();
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
        result_valence /= list.length()+1;
        result_arousal /= list.length()+1;

        //happiness 일때 좋아요를 누른 곡의 평균
        means[0] = Math.round(result_valence*10000)/10000.0;
        means[1] = Math.round(result_arousal*10000)/10000.0;

        // DB 에 보낸다.
        presentUser = mem.get();
        switch (emotion){
            case "anger":
                presentUser.setAngerV(means[0]);
                presentUser.setAngerA(means[1]);
                break;
            case "happiness":
                presentUser.setHappinessV(means[0]);
                presentUser.setHappinessA(means[1]);
                break;
            case "disgust":
                presentUser.setDisgustV(means[0]);
                presentUser.setDisgustA(means[1]);
                break;
            case "fear":
                presentUser.setFearV(means[0]);
                presentUser.setFearA(means[1]);
                break;
            case "neutral":
                presentUser.setNeutralV(means[0]);
                presentUser.setNeutralA(means[1]);
                break;
            case "sadness":
                presentUser.setSadnessV(means[0]);
                presentUser.setSadnessA(means[1]);
                break;
            case "surprise":
                presentUser.setSurpriseV(means[0]);
                presentUser.setSurpriseA(means[1]);
                break;
        }
        service.update(presentUser);
        System.out.println(presentUser.getHappinessV());

        dto.setRes(true);
        return ResponseEntity.accepted().headers(headers).body(dto);
    }
}
