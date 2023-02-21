package com.playdata.moodMusicRecommend.recommend;

import com.playdata.moodMusicRecommend.member.Member;
import com.playdata.moodMusicRecommend.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;


@Controller
@RequestMapping("/recommend")
@RequiredArgsConstructor
public class RecommendController {

    @Autowired
    MemberRepository memberRepository;

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

}
