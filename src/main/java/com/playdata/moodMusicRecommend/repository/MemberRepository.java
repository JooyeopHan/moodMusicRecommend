package com.playdata.moodMusicRecommend.repository;

import com.playdata.moodMusicRecommend.entity.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {

    // 로그인시 사용자 조회 기능
    Optional<Member> findByNickname(String nickname);
    // 회원 탈퇴
    void deleteByNickname(String nickname);

}
