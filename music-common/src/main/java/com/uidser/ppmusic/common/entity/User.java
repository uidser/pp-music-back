package com.uidser.ppmusic.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Long id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String phone;
    private String profilePicture;
    private Date createTime;
    private Date updateTime;
    private Date logoutTime;
    private Integer isEnable;

}
