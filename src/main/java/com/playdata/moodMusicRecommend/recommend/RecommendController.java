package com.playdata.moodMusicRecommend.recommend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;


@Controller
@RequestMapping("/recommend")
@RequiredArgsConstructor
public class RecommendController {


    @PostMapping("/music")
    @ResponseBody
    public String music(MultipartHttpServletRequest mhsr) throws IOException {
        MultiValueMap<String,String> builder = new LinkedMultiValueMap<>();
        System.out.println(mhsr.getParameter("file1").substring(22));
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
        return result;
    }

}