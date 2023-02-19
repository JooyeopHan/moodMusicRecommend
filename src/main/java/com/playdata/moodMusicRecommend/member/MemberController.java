package com.playdata.moodMusicRecommend.member;

import com.playdata.moodMusicRecommend.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    public ResponseEntity<ResultDTO> login(@AuthenticationPrincipal User user,HttpSession session){
        System.out.println("session id" + session.getId());
        System.out.println("user정보 제발 :" + user.getUsername());
        System.out.println("user정보 제발 :" + user.getAuthorities());


        ResultDTO dto = new ResultDTO();
        dto.setMsg("로그인 성공하였습니다");
        dto.setRes(true);
        dto.setUrl("/");

        return ResponseEntity.accepted().headers(new HttpHeaders()).body(dto);
    }
    @GetMapping("/logout")
    public ResponseEntity<ResultDTO> logout(@AuthenticationPrincipal User user, HttpSession session){
        System.out.println("로그 아웃 시 session id" + session.getId());
//        System.out.print("user정보 제발 :" + user.getUsername());
//        System.out.print("user정보 제발 :" + user.getAuthorities());

        ResultDTO dto = new ResultDTO();
        dto.setMsg("로그아웃 성공하였습니다");
        dto.setRes(true);
        dto.setUrl("/");
        return ResponseEntity.accepted().headers(new HttpHeaders()).body(dto);
    }

    @GetMapping("/error")
    public ResponseEntity<ResultDTO> error(HttpRequest request, Exception exception, HttpSession session){
        System.out.println("session id" + session.getId());
        System.out.println("Exception" + exception.getMessage());

//        System.out.print("use" + use.getMessage());

        ResultDTO dto = new ResultDTO();
        dto.setMsg("요청이 실패하였습니다");
        dto.setRes(false);
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

        ResultDTO dto = new ResultDTO();
        //실패시
        HttpHeaders headers = new HttpHeaders();
        dto.setUrl("/register");
        dto.setRes(false);
        dto.setMsg("회원가입 실패하였습니다.");

        if (bindingResult.hasErrors()) {
//            return "signup_page";
            return ResponseEntity.accepted().headers(headers).body(dto);

        }
        if (!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
//            return "signup_page";
            return ResponseEntity.accepted().headers(headers).body(dto);

        }
        try{
            service.create(memberCreateForm.getNickname(), memberCreateForm.getEmail(), memberCreateForm.getPassword1());
        } catch (DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자 입니다.");
//            return "signup_page";
            return ResponseEntity.accepted().headers(headers).body(dto);

        }catch (Exception e){
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
//            return "signup_page";
            return ResponseEntity.accepted().headers(headers).body(dto);

        }

        dto.setUrl("/");
        dto.setRes(true);
        dto.setMsg("회원가입을 성공적으로 완료 하였습니다.");

        return ResponseEntity.accepted().headers(headers).body(dto);
    }

    @PostMapping("/delete")
    @Transactional
    public String delete(@AuthenticationPrincipal User user){

        System.out.println("로그인한 멤버 아이디 : "+user.getUsername());

        service.delete(user);

        return "redirect:/";
    }



}
