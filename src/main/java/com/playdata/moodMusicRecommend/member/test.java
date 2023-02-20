package com.playdata.moodMusicRecommend.member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recommend")
public class test {

    @PostMapping("/list")
    public void test(@RequestBody testVO model){
        System.out.println(model.getNickname());
        System.out.println(model.getList());
        System.out.println(model.getResult());
    }
}
