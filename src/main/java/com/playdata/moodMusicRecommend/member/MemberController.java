package com.playdata.moodMusicRecommend.member;

import com.playdata.moodMusicRecommend.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;


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
    public ResponseEntity<ResultDTO> login(){

        ResultDTO dto = new ResultDTO();
        dto.setMsg("로그인 성공하였습니다");
        dto.setRes(true);
        dto.setUrl("/");
        return ResponseEntity.accepted().headers(new HttpHeaders()).body(dto);
    }
    @GetMapping("/logout")
    public ResponseEntity<ResultDTO> logout(){

        ResultDTO dto = new ResultDTO();
        dto.setMsg("로그아웃 성공하였습니다");
        dto.setRes(true);
        dto.setUrl("/");
        return ResponseEntity.accepted().headers(new HttpHeaders()).body(dto);
    }

    @GetMapping("/delete")
    public String delete(){return "delete_page";}


    @PostMapping("/signup")
    @CrossOrigin(origins = "*")
//    @ResponseBody
    public ResponseEntity<?> signup(@Valid @RequestBody MemberCreateForm memberCreateForm
                                , BindingResult bindingResult) throws URISyntaxException {
        System.out.println(memberCreateForm.getNickname());
        System.out.println(memberCreateForm.getPassword1());

        if (bindingResult.hasErrors()) {
//            return "signup_page";
            return ResponseEntity.created(new URI("/register")).body(bindingResult);

        }
        if (!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
//            return "signup_page";
            return ResponseEntity.created(new URI("/register")).body(bindingResult);

        }
        try{
            service.create(memberCreateForm.getNickname(), memberCreateForm.getEmail(), memberCreateForm.getPassword1());
        } catch (DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자 입니다.");
//            return "signup_page";
            return ResponseEntity.created(new URI("/register")).body(bindingResult);

        }catch (Exception e){
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
//            return "signup_page";
            return ResponseEntity.created(new URI("/register")).body(bindingResult);

        }
        HttpHeaders headers = new HttpHeaders();

       ResultDTO dto = new ResultDTO();
        dto.setUrl("/");
        dto.setRes(true);
        dto.setMsg("회원가입을 성공적으로 완료 하였습니다.");
//        return "redirect:/";
        return ResponseEntity.accepted().headers(headers).body(dto);
    }
//    @PostMapping("/login")
//    @Transactional
//    public String login(@RequestBody Model model){
//        System.out.println("테스트");
//        System.out.println("username" +model.getAttribute("username"));
//        System.out.println("passswd" + model.getAttribute("passwd"));
//
//        return "redirect:/";
//    }
    @PostMapping("/delete")
    @Transactional
    public String delete(@AuthenticationPrincipal User user){

        System.out.println("로그인한 멤버 아이디 : "+user.getUsername());

        service.delete(user);

        return "redirect:/";
    }



}
