package com.playdata.moodMusicRecommend.service;

import com.playdata.moodMusicRecommend.entity.Member;
import com.playdata.moodMusicRecommend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(String nickname, String email, String passwd){
        Member member = new Member();
        member.setNickname(nickname);
        member.setEmail(email);

        member.setPasswd(passwordEncoder.encode(passwd)); //passwd 암호화

        this.memberRepository.save(member);

        return member;
    }

    public Optional<Member> select(String nickname){
        Optional<Member> member =  memberRepository.findByNickname(nickname);

        return member;
    }

    public void delete(User user) {
        memberRepository.deleteByNickname(user.getUsername());}

    // 변경 사항 저장
    public void update(Member member){
        this.memberRepository.save(member);
    }

    public boolean testing(String test,User user){

        Member presentUser;

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


        for(int i = 0; i<list.length();i++){
            JSONObject obj = list.getJSONObject(i);
            double valence = obj.getDouble("valence");
            double arousal = obj.getDouble("arousal");
            result_valence += valence-0.5;
            result_arousal += arousal-0.5;
        }
        result_valence /= list.length();
        result_arousal /= list.length();

        //happiness 일때 좋아요를 누른 곡의 평균
        means[0] = 2*Math.round(2*result_valence*10000)/10000.0;
        means[1] = 2*Math.round(2*result_arousal*10000)/10000.0;

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
        this.update(presentUser);
        return true;
    }

}
