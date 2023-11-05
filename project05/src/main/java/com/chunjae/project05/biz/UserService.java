package com.chunjae.project05.biz;

import com.chunjae.project05.domain.UserPrincipal;
import com.chunjae.project05.entity.Role;
import com.chunjae.project05.entity.User;
import com.chunjae.project05.entity.UserRole;
import com.chunjae.project05.entity.UserVO;
import com.chunjae.project05.persistence.RoleMapper;
import com.chunjae.project05.persistence.UserMapper;
import com.chunjae.project05.persistence.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findUserByLoginId(String loginId) {
        return userMapper.findUserByLoginId(loginId);
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        userMapper.setUserInfo(user);
        User member = userMapper.findUserByLoginId(user.getLoginId());
        Role role = roleMapper.getRoleInfo("USER");
        UserRole userRole = new UserRole();
        userRole.setRoleId(role.getId());
        userRole.setUserId(member.getId());
        userRoleMapper.setUserRoleInfo(userRole);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVO userVO = userMapper.findUserListByLoginId(username);
        return new UserPrincipal(userVO);
    }

}