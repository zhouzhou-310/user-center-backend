package com.zhouzhou.usercenterbackend.service;

import com.zhouzhou.usercenterbackend.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author zhouzhou-310
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-05-07 22:22:28
*/
public interface UserService extends IService<User> {

    /**
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @param number
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String number);

    /**
     * @param userAccount
     * @param userPassword
     * @return
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param user
     * @return
     */
    User getSafeUser(User user);
}
