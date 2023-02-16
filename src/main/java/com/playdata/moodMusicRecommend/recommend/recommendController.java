package com.playdata.moodMusicRecommend.recommend;

import com.playdata.moodMusicRecommend.ResultDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@Controller
@RequestMapping("/recommend")
public class recommendController {

    @PostMapping("/music")
    public ResponseEntity<ResultDTO> music(MultipartHttpServletRequest multipartHttpServletRequest){

//        System.out.println("image before :" + files.get(0));
//        System.out.println("image before :" + files.get(1));
        System.out.println("Multipart image " + multipartHttpServletRequest.getParameter("imgBefore"));
        System.out.println("Multipart image " + multipartHttpServletRequest.getParameter("imgAfter"));
        ResultDTO dto = new ResultDTO();


        dto.setMsg("음악 추천 요청 성공하였습니다");
        dto.setRes(true);
        dto.setUrl("/register");//임시용 (추후 추천 화면생길시 변경)

        return ResponseEntity.accepted().headers(new HttpHeaders()).body(dto);
    }
}
