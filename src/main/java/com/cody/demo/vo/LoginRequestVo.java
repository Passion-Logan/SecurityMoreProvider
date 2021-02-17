package com.cody.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class LoginRequestVo  implements Serializable {
    public static final Long Serializable = 1L;

    private String username;

    private String password;

    private String authType;

}
