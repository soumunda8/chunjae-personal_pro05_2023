package com.chunjae.project05.persistence;

import com.chunjae.project05.entity.User;
import com.chunjae.project05.entity.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    User findUserByLoginId(@Param("loginId") String loginId);
    UserVO findUserListByLoginId(@Param("loginId") String loginId);
    int setUserInfo(@Param("param") User param);

}