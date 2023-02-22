package com.playdata.moodMusicRecommend.recommend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.playdata.moodMusicRecommend.ResultDTO;
import com.playdata.moodMusicRecommend.member.Member;
import com.playdata.moodMusicRecommend.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/recommend")
@RequiredArgsConstructor
public class RecommendController {

    @Autowired
    MemberRepository memberRepository;
    private final RecommendService service;
    private final listService listservice;

    @PostMapping("/music")
    @ResponseBody
    public String music(MultipartHttpServletRequest mhsr,@AuthenticationPrincipal User user) throws IOException {
        MultiValueMap<String,Object> builder = new LinkedMultiValueMap<>();
        System.out.println(mhsr.getParameter("file1").substring(22));
// decode base64 encoded image data
        String file1Data = mhsr.getParameter("file1").substring(22);
        String file2Data = mhsr.getParameter("file2").substring(22);

// create multipart body parts and add them to the builder
        builder.add("file1", file1Data);
        builder.add("file2", file2Data);
        Optional<Member> mem = memberRepository.findByNickname(user.getUsername());
        mem.ifPresent(member ->  builder.add("member", member));

        WebClient client = WebClient.create();

        String result = client.post()
                .uri("http://localhost:9000/detecting")
                .bodyValue(builder)
                .retrieve()
                .bodyToMono(String.class).block();
        return result;
    }

    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<ResultDTO> Like(@RequestBody Map<String, Object> test, @AuthenticationPrincipal User user) throws JsonProcessingException {
        System.out.println("recommend/list 접속");
        ResultDTO dto = new ResultDTO();
        HttpHeaders headers = new HttpHeaders();
        dto.setMsg("성공적으로 저장되었습니다.");
        dto.setRes(false);
        dto.setUrl("/emotion");

        // 데이터 저장

        service.create(test);

        System.out.println("성공?");

        // 추출된 데이터 기반 갱신
        ObjectMapper mapper = new ObjectMapper();
        String testing = mapper.writeValueAsString(test);
        String testing1 = test.toString();
        System.out.println(testing1);
        System.out.println(testing);
        boolean result = listservice.testing(testing,user);

        // 갱신된 결과 User 저장

        dto.setRes(result);
        return ResponseEntity.accepted().headers(headers).body(dto);
    }
}
