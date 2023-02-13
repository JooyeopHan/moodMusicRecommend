package com.playdata.moodMusicRecommend.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
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

    @GetMapping("/delete")
    public String delete(){return "delete_page";}


    @PostMapping("/signup")
    @CrossOrigin(origins = "*")
    @ResponseBody
    public MemberCreateForm signup(@Valid @RequestBody MemberCreateForm memberCreateForm, BindingResult bindingResult) {
        System.out.println(memberCreateForm.getNickname());
        System.out.println(memberCreateForm.getPassword1());
        System.out.println(bindingResult);
        if (bindingResult.hasErrors()) {
//            return "signup_page";
            return memberCreateForm;

        }
        if (!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
//            return "signup_page";
            return memberCreateForm;

        }
        try{
            service.create(memberCreateForm.getNickname(), memberCreateForm.getEmail(), memberCreateForm.getPassword1());
        } catch (DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자 입니다.");
//            return "signup_page";
            return memberCreateForm;

        }catch (Exception e){
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
//            return "signup_page";
            return memberCreateForm;

        }

//        return "redirect:/";
        return memberCreateForm;
    }

    @PostMapping("/delete")
    @Transactional
    public String delete(@AuthenticationPrincipal User user){

        System.out.println("로그인한 멤버 아이디 : "+user.getUsername());

        service.delete(user);

        return "redirect:/";
    }



}
