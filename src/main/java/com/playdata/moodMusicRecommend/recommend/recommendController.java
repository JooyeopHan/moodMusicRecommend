package com.playdata.moodMusicRecommend.recommend;

import com.playdata.moodMusicRecommend.ResultDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/recommend")
public class recommendController {

    @PostMapping("/music")
    public ResponseEntity<ResultDTO> music(MultipartHttpServletRequest mhsr) {


        String path = "/resources/images";
//        System.out.println("image before :" + files.get(0));
//        System.out.println("image before :" + files.get(1));
        System.out.println("Multipart image " + mhsr.getParameter("file"));
        System.out.println("Multipart image " + mhsr);
//        System.out.println("Multipart image " + mhsr.getParameter("imgAfter"));
        try{
            // MultipartHttpServletRequest 생성
            Iterator<String> iter = mhsr.getFileNames();

            System.out.println("iter" + iter);
            System.out.println("iter" + iter.hasNext());
            MultipartFile mfile = null;

            mfile = mhsr.getFile("image/jpeg;base64");
            System.out.println("mfile1" + mfile);
            mfile = mhsr.getFile("data");
            System.out.println("mfile1" + mfile);
            mfile = mhsr.getFile("file1");
            System.out.println("mfile2" + mfile);


            String fieldName = "";
            List resultList = new ArrayList();

            // 디레토리가 없다면 생성
            File dir = new File(path);
            if (!dir.isDirectory()) {
                dir.mkdirs();
            }

            while (iter.hasNext()) {
                fieldName = (String) iter.next(); // 내용을 가져와서
                System.out.println("fieldName : " + fieldName);
                mfile = mhsr.getFile(fieldName);
                System.out.println("mfile : " + mfile);

                String origName;
                origName = new String(mfile.getOriginalFilename().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8); //한글꺠짐 방지

                System.out.println("origName: " + origName);
                // 파일명이 없다면
                if ("".equals(origName)) {
                    continue;
                }

            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        ResultDTO dto = new ResultDTO();


        dto.setMsg("음악 추천 요청 성공하였습니다");
        dto.setRes(true);
        dto.setUrl("/register");//임시용 (추후 추천 화면생길시 변경)

        return ResponseEntity.accepted().headers(new HttpHeaders()).body(dto);

    }
}
