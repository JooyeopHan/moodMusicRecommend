package com.playdata.moodMusicRecommend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.playdata.moodMusicRecommend.model.dto.ResultDTO;
import com.playdata.moodMusicRecommend.entity.Member;
import com.playdata.moodMusicRecommend.entity.Recommend;
import com.playdata.moodMusicRecommend.repository.MemberRepository;
import com.playdata.moodMusicRecommend.service.MemberService;
import com.playdata.moodMusicRecommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping("/recommend")
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    @PostMapping("/music")
    @ResponseBody
    public String music(MultipartHttpServletRequest mhsr ,@AuthenticationPrincipal User user) throws IOException {
        MultiValueMap<String, Object> builder = new LinkedMultiValueMap<>();

        String file1Data = mhsr.getParameter("file1").substring(22);
        String file2Data = mhsr.getParameter("file2").substring(22);


        builder.add("file1", file1Data);
        builder.add("file2", file2Data);
        Optional<Member> mem = memberRepository.findByNickname(user.getUsername());
        mem.ifPresent(member ->  builder.add("member", member));

        WebClient client = WebClient.create();

        String result = client.post()
                .uri("http://localhost:9000/detecting") // Flask Rest API ??????
                .bodyValue(builder)
                .retrieve()
                .bodyToMono(String.class).block();
        System.out.println("result" + result);
        return result;
    }

    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<ResultDTO> Like(@RequestBody Map<String, Object> test, @AuthenticationPrincipal User user) throws JsonProcessingException {
        ResultDTO dto = new ResultDTO();
        HttpHeaders headers = new HttpHeaders();
        dto.setMsg("??????????????? ?????????????????????.");
        dto.setRes(false);
        dto.setUrl("/emotion");

        // ????????? ??????
        recommendService.create(test);

        // ????????? ????????? ?????? ??????
        ObjectMapper mapper = new ObjectMapper();
        String testing = mapper.writeValueAsString(test);
        String testing1 = test.toString();
        System.out.println(testing1);
        System.out.println(testing);
        boolean result = memberService.testing(testing, user);

        // ????????? ?????? User ??????
        dto.setRes(result);
        return ResponseEntity.accepted().headers(headers).body(dto);
    }

    @PostMapping("/profile")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> showUserMusicList(@AuthenticationPrincipal User user) {
        HttpHeaders headers = new HttpHeaders();
        String username = user.getUsername();

        List<Recommend> musicList = recommendService.select(username);

        return ResponseEntity.accepted().headers(headers).body(musicList);
    }

}