package com.chunjae.project05.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;
    private int active;
    private String loginId;
    private String userName;
    private String password;
    private String passwordConfirm;
    private String email;
    private String tel;
    private String addr1;
    private String addr2 = "";
    private String postcode;
    private String regDate;
    private String birth;
    private int pt;
    private int visited;
    private int roleId = 99;
    private boolean useYn;

}