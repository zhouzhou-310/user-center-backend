package com.zhouzhou.usercenterbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhouzhou.usercenterbackend.model.User;
import com.zhouzhou.usercenterbackend.service.UserService;
import com.zhouzhou.usercenterbackend.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author zhouzhou-310
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-05-07 22:22:28
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    /**
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @param number
     * @return
     */
    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword, String number) {
        // 校验数据
        if (StringUtils.isAllBlank(userAccount, userPassword, checkPassword, number)) {
            return null;
        }
        if (userAccount.length() > 4) {
            return null;
        }
        if (userPassword.length() > 8 || checkPassword.length() > 8) {
            return null;
        }
        String validPattern = "^[a-zA-Z0-9_.-\\u4e00-\\u9fff\\u3400-\\u4dbf\\uf900-\\ufaff\\u20000-\\u2a6df]+$";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (!matcher.find()) {
            return null;
        }
        if (!userPassword.equals(checkPassword)) {
            return null;
        }



        return null;
    }

    /**
     * @param userAccount
     * @param userPassword
     * @return
     */
    @Override
    public User userLogin(String userAccount, String userPassword) {
        return null;
    }
}




