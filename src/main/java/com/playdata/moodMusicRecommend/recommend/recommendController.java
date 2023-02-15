package com.playdata.moodMusicRecommend.recommend;

import com.playdata.moodMusicRecommend.ResultDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/recommend")
public class recommendController {

    @PostMapping("/music")
    public ResponseEntity<ResultDTO> music(@RequestBody HttpRequest request, MultipartFile image){
        System.out.println("request" + request);
        System.out.println("request URI" + request.getURI());
        System.out.println("Multipart image " + image);
        ResultDTO dto = new ResultDTO();


        dto.setMsg("음악 추천 요청 성공하였습니다");
        dto.setRes(true);
        dto.setUrl("/register");//임시용 (추후 추천 화면생길시 변경)

        return ResponseEntity.accepted().headers(new HttpHeaders()).body(dto);
    }
}
