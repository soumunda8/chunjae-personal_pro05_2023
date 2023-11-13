package com.chunjae.project05.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    private Long id;
    private int active;
    private String loginId;
    private String userName;
    private String password;
    private int roleId = 99;
    private String roleNm;

}