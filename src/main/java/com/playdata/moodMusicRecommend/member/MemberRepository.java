package com.playdata.moodMusicRecommend.member;

import com.playdata.moodMusicRecommend.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 로그인시 사용자 조회 기능
    Optional<Member> findByNickname(String nickname);

}
