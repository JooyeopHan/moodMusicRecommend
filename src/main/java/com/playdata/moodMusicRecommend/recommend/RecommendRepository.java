package com.playdata.moodMusicRecommend.recommend;

import com.playdata.moodMusicRecommend.member.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RecommendRepository extends CrudRepository<Recommend, Long> {
    List<Recommend> findByUsername(String username);

}
