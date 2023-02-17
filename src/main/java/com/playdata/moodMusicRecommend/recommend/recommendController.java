package com.playdata.moodMusicRecommend.recommend;

import com.playdata.moodMusicRecommend.ResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
@RequestMapping("/recommend")
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService service;

    @PostMapping("/music")
    public ResponseEntity<ResultDTO> music(HttpServletRequest req) throws IOException {

        MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) req;

        System.out.println(mhsr.getAttribute("file1"));
        String path = req.getSession().getServletContext().getRealPath("/resources");

        System.out.println("path" + path);

        MultipartFile mfile = null;
        String fieldName = "";
        List resultList = new ArrayList();
        Map returnObject = new HashMap();
        int index = 0;
        try {

            Iterator iter = mhsr.getFileNames();
            System.out.println("iter" + iter);
            // 디레토리가 없다면 생성
            File dir = new File(path);
            if (!dir.isDirectory()) {
                dir.mkdirs();
            }

            // 값이 나올때까지
            while (iter.hasNext()) {
                index +=1;
                fieldName = (String) iter.next(); // 내용을 가져와서
                mfile = mhsr.getFile(fieldName);
                String origName;
                origName = new String(mfile.getOriginalFilename().getBytes("8859_1"), "UTF-8"); //한글꺠짐 방지

                System.out.println("origName: " + origName);
                // 파일명이 없다면
                if ("".equals(origName)) {
                    continue;
                }

                String saveFileName = "image"+index+".png";

                System.out.println("saveFileName : " + saveFileName);

                // 설정한 path에 파일저장
                File serverFile = new File(path + File.separator + saveFileName);
                mfile.transferTo(serverFile);

                resultList.add(serverFile);

            }

            System.out.println("resultlist" + resultList.get(0));
            System.out.println("resultlist.get(0)" + resultList.get(0));
            System.out.println("resultlist.get(1)" + resultList.get(1));

//            service.recommendMusic(resultList);


        } catch (UnsupportedEncodingException e)
        {

            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResultDTO dto = new ResultDTO();
        dto.setMsg("음악 추천 요청 성공하였습니다");
        dto.setRes(true);
        dto.setUrl("/register");//임시용 (추후 추천 화면생길시 변경)

        return ResponseEntity.accepted().headers(new HttpHeaders()).body(dto);
    }
}
