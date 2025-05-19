package com.zhouzhou.usercenterbackend.controller;

import com.zhouzhou.usercenterbackend.model.User;
import com.zhouzhou.usercenterbackend.model.request.UserLoginRequest;
import com.zhouzhou.usercenterbackend.model.request.UserRegisterRequest;
import com.zhouzhou.usercenterbackend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: zhouzhou-310
 * @createTime: 2025-05-19  17:10
 * @description: TODO
 * @version: 1.0
 */
public class UserController {
    @Resource
    private UserService userService;

    /**
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public long userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest == null) {
            return -1;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            return -1;
        }
        long l = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        return l;
    }

    /**
     * @param userLoginRequest 用户登录请求体
     * @param request
     * @return
     */
    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return user;
    }
}
