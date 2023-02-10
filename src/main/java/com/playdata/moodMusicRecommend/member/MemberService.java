package com.playdata.moodMusicRecommend.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository repository;
    public boolean login(String nickname, String passwd) {
        return passwd.equals("@1234") && nickname.equals("spring");
    }

    public Member create(String nickname, String email, String passwd){
        Member member = new Member();
        member.setNickname(nickname);
        member.setEmail(email);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        member.setPasswd(passwordEncoder.encode(passwd)); //passwd 암호화

        this.repository.save(member);

        return member;
    }
}
