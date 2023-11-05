package com.chunjae.project05.entity;

import lombok.Data;

@Data
public class UserVO {

    private int id;
    private int active;
    private String loginId;
    private String userName;
    private String password;
    private int roleId = 4;
    private String roleNm;

}