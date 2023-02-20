package com.playdata.moodMusicRecommend.member;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    public Member create(String nickname, String email, String passwd){
        Member member = new Member();
        member.setNickname(nickname);
        member.setEmail(email);

        member.setPasswd(passwordEncoder.encode(passwd)); //passwd 암호화

        this.repository.save(member);

        return member;
    }

    public Optional<Member> select(String nickname){
        Optional<Member> member =  repository.findByNickname(nickname);

        return member;
    }

    public void delete(User user) {
        repository.deleteByNickname(user.getUsername());}

}
