package com.playdata.moodMusicRecommend.member;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException{
        Optional<Member> _siteUser = this.repository.findByNickname(nickname);
        if(_siteUser.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        Member siteUser = _siteUser.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(nickname)){
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        return new User(siteUser.getNickname(), siteUser.getPasswd(), authorities);

    }


}
