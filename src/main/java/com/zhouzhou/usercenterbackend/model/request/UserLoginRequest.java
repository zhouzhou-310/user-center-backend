package com.zhouzhou.usercenterbackend.model.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author: zhouzhou-310
 * @createTime: 2025-05-19  17:08
 * @description: TODO
 * @version: 1.0
 */
@Data
public class UserLoginRequest implements Serializable {


    @Serial
    private static final long serialVersionUID = 4969367322503148108L;

    private String userAccount;

    private String userPassword;
}
