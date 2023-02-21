package com.playdata.moodMusicRecommend.recommend;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/recommend")
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService service;
    @PostMapping("/music")
    @ResponseBody
    public String music(MultipartHttpServletRequest mhsr) throws IOException {
        MultiValueMap<String, String> builder = new LinkedMultiValueMap<>();
// decode base64 encoded image data
        String file1Data = mhsr.getParameter("file1").substring(22);
        String file2Data = mhsr.getParameter("file2").substring(22);

// create multipart body parts and add them to the builder
        builder.add("file1", file1Data);
        builder.add("file2", file2Data);

        WebClient client = WebClient.create();

        String result = client.post()
                .uri("http://localhost:9000/detecting")
                .bodyValue(builder)
                .retrieve()
                .bodyToMono(String.class).block();
        System.out.println("result" + result);
        return result;
    }

    @PostMapping("/list")
    @ResponseBody
    public String Like(@RequestBody Map<String, Object> test, @AuthenticationPrincipal User user) {
        System.out.println("recommend/list 접속");


        // 데이터 저장

        service.create(test);

        System.out.println("성공?");
        // 저장된 데이터 추출

        // 추출된 데이터 기반 갱신

        // 갱신된 결과 User 저장

        return "hello";

    }


}