package com.playdata.moodMusicRecommend.member;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<Member> _siteUser = this.repository.findByNickname(username);
        if(_siteUser.isEmpty()){
            System.out.print("사용자를 찾을 수 없습니다.");
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        System.out.print("사용자를 찾았습니다.");
        Member siteUser = _siteUser.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)){
            System.out.print("admin게정입니다.");
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            System.out.print("User계정입니다.");
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        return new User(siteUser.getNickname(), siteUser.getPasswd(), authorities);

    }


}
