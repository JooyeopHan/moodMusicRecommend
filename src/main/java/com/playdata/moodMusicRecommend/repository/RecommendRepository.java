package com.playdata.moodMusicRecommend.repository;

import com.playdata.moodMusicRecommend.entity.Recommend;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecommendRepository extends CrudRepository<Recommend, Long> {
    List<Recommend> findByUsername(String username);

}
