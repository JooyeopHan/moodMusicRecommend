package com.playdata.moodMusicRecommend.util;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
@Getter
@Setter
public class MemberCreateForm {
    @Size(min = 3, max = 25, message = "아이디를 3자 이상 25자 이하로 입력해주세요.")
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String nickname;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String email;
}
