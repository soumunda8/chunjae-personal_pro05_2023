package com.chunjae.project05.domain;

import com.chunjae.project05.entity.UserVO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class UserPrincipal implements UserDetails {

    private UserVO userVO;

    public UserPrincipal(UserVO userVO){
        this.userVO = userVO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return "ROLE_" + userVO.getRoleNm();
            }

        });

        return collectors;
    }

    @Override
    public String getPassword() {
        return userVO.getPassword();
    }

    @Override
    public String getUsername() {
        return userVO.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userVO.getActive() == 1;
    }

    public String getId() {
        return userVO.getLoginId();
    }

    public String getName(){
        return userVO.getUserName();
    }

}