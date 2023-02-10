package com.playdata.moodMusicRecommend.member;

import com.playdata.moodMusicRecommend.model.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


@Controller
@RequestMapping(value = "/member")
public class MemberController {
    @Autowired
    MemberService service;

    @GetMapping("/signup")
    public String signup(MemberCreateForm memberCreateForm){

        return "signup_page";
    }

    @GetMapping("/login")
    public String login(){
        return "login_page";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "signup_page";
        }
        if (!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())){
            bindingResult.rejectValue("password2","passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "signup_page";
        }

        service.create(memberCreateForm.getNickname(), memberCreateForm.getEmail(), memberCreateForm.getPassword1());

        return "redirect:/";
    }

}
