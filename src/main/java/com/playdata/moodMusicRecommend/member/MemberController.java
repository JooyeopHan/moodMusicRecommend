package com.playdata.moodMusicRecommend.member;

import com.playdata.moodMusicRecommend.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;


@Controller
@RequestMapping(value = "/member")
public class MemberController {
    @Autowired
    MemberService memberService;


    @GetMapping("/signup")
    public String signup(MemberCreateForm memberCreateForm) {

        return "signup_page";
    }

    @GetMapping("/login")
    public ResponseEntity<ResultDTO> login(@AuthenticationPrincipal User user, HttpSession session) {

        ResultDTO dto = new ResultDTO();
        dto.setMsg("로그인 성공하였습니다");
        dto.setRes(true);
        dto.setUrl("/");

        return ResponseEntity.accepted().headers(new HttpHeaders()).body(dto);
    }

    @GetMapping("/logout")
    public ResponseEntity<ResultDTO> logout(@AuthenticationPrincipal User user, HttpSession session) {
        System.out.println("로그 아웃 시 session id" + session.getId());

        ResultDTO dto = new ResultDTO();
        dto.setMsg("로그아웃 성공하였습니다");
        dto.setRes(true);
        dto.setUrl("/");
        return ResponseEntity.accepted().headers(new HttpHeaders()).body(dto);
    }

    @GetMapping("/error")
    public ResponseEntity<ResultDTO> error(HttpSession session) {
        System.out.println("session id" + session.getId());

        ResultDTO dto = new ResultDTO();
        dto.setMsg("아이디와 비밀번호를 확인해주세요.");
        dto.setRes(false);
        dto.setUrl("/");
        return ResponseEntity.accepted().headers(new HttpHeaders()).body(dto);
    }

    @GetMapping("/delete")
    public String delete() {
        return "delete_page";
    }


    @PostMapping("/signup")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> signup(@Valid @RequestBody MemberCreateForm memberCreateForm
            , BindingResult bindingResult) throws URISyntaxException {

        ResultDTO dto = new ResultDTO();
        //실패시
        HttpHeaders headers = new HttpHeaders();
        dto.setUrl("/register");
        dto.setRes(false);
        //@Vaild 유효성 검사 실패시
        if (bindingResult.hasErrors()) {
            dto.setMsg(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            return ResponseEntity.accepted().headers(headers).body(dto);

        }
        //패스워드 2개 불일치시
        if (!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            dto.setMsg(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            return ResponseEntity.accepted().headers(headers).body(dto);

        }
        try {
            memberService.create(memberCreateForm.getNickname(), memberCreateForm.getEmail(), memberCreateForm.getPassword1());
        } catch (DataIntegrityViolationException e) { // 이미 등록된 사용자 일시
            e.printStackTrace();
            bindingResult.rejectValue("nickname","signupFailed", "이미 등록된 사용자 입니다.");
            dto.setMsg(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            return ResponseEntity.accepted().headers(headers).body(dto);


        } catch (Exception e) { // 그외 오류 발생시
            e.printStackTrace();
            bindingResult.rejectValue("etc","signupFailed", e.getMessage());
            dto.setMsg(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            return ResponseEntity.accepted().headers(headers).body(dto);
        }

        // 회원가입 성공 시
        dto.setUrl("/");
        dto.setRes(true);
        dto.setMsg("회원가입을 성공적으로 완료 하였습니다.");

        return ResponseEntity.accepted().headers(headers).body(dto);
    }

    @PostMapping("/delete")
    @Transactional
    public ResponseEntity<ResultDTO> delete(@AuthenticationPrincipal User user) {
        ResultDTO dto = new ResultDTO();
        System.out.println("로그인한 멤버 아이디 : " + user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        memberService.delete(user);

        SecurityContextHolder.clearContext(); // 유저 로그아웃
        dto.setMsg("회원탈퇴를 성공적으로 하였습니다.");
        dto.setRes(true);
        dto.setUrl("/");

        return ResponseEntity.accepted().headers(headers).body(dto);

    }

    @PostMapping("/auth")
    @CrossOrigin(origins = "*")
    public ResponseEntity<ResultDTO> authentication(@AuthenticationPrincipal User user) {
        HttpHeaders headers = new HttpHeaders();
        ResultDTO dto = new ResultDTO();
        if(user==null) {
            dto.setAuth("ROLE_NO");
        }
        else{
            String auth = user.getAuthorities().toString();
            if (auth.equals("[ROLE_USER]")){
                dto.setAuth("ROLE_USER");
            } else if (auth.equals("[ROLE_ADMIN]")) {
                dto.setAuth("ROLE_ADMIN");
            }
        }

        dto.setRes(true);
        return ResponseEntity.accepted().headers(headers).body(dto);
    }

    @PostMapping("/profile")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> showProfile(@AuthenticationPrincipal User user) {
        HttpHeaders headers = new HttpHeaders();
        String username = null;
        if (user != null){
            username = user.getUsername();
        }

        Optional<Member> profile = memberService.select(username);

        profile.ifPresent(System.out::println);

        return ResponseEntity.accepted().headers(headers).body(profile);
    }
}
