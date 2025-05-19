package com.zhouzhou.usercenterbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhouzhou.usercenterbackend.model.User;
import com.zhouzhou.usercenterbackend.service.UserService;
import com.zhouzhou.usercenterbackend.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zhouzhou.usercenterbackend.content.UserContent.USER_LOGIN_STATE;

/**
* @author zhouzhou-310
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-05-07 22:22:28
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{


    @Resource
    UserMapper userMapper;
    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "zhou";

    /**
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @param userNumber
     * @return
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String userNumber) {
        // 校验数据
        // 非空
        if (StringUtils.isAllBlank(userAccount, userPassword, checkPassword, userNumber)) {
            return -1;
        }
        // 账户长度不小于 4 位
        if (userAccount.length() > 4) {
            return -1;
        }
        // 密码就不小于 8 位
        if (userPassword.length() > 8 || checkPassword.length() > 8) {
            return -1;
        }
        // 账户不包含特殊字符
        String validPattern = "^[a-zA-Z0-9_.-\\u4e00-\\u9fff\\u3400-\\u4dbf\\uf900-\\ufaff\\u20000-\\u2a6df]+$";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (!matcher.find()) {
            return -1;
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        // 账户不能重复
        QueryWrapper<User> queryWrapperUserAccount = new QueryWrapper<>();
        queryWrapperUserAccount.eq("userAccount", userAccount);
        long countAccount = userMapper.selectCount(queryWrapperUserAccount);
        if (countAccount > 0) {
            return -1;
        }
        // 用户编号不能重复
        QueryWrapper<User> queryWrapperNumber =new QueryWrapper<>();
        queryWrapperNumber.eq("userNumber", userNumber);
        long selectCount = userMapper.selectCount(queryWrapperNumber);
        if (selectCount > 0) {
            return -1;
        }
        // 加密密码
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 插入数据
        User user =new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setUserNumber(userNumber);
        boolean saveResult = this.save(user);
        if (!(saveResult)) {
            return -1;
        }
        return user.getId();
    }

    /**
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 校验数据
        if(StringUtils.isAllBlank(userAccount, userPassword)){
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }
        if (userPassword.length() < 8) {
            return null;
        }
        String validPattern = "^[a-zA-Z0-9_.-\\u4e00-\\u9fff\\u3400-\\u4dbf\\uf900-\\ufaff\\u20000-\\u2a6df]+$";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (!matcher.find()) {
            return null;
        }
        // 用户输入密码加密
        String encryptPasswords = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 校验 userAccount 是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPasswords);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            return null;
        }
        // 用户脱敏
        User safeUser = getSafeUser(user);
        //  记录用户登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safeUser);
        return safeUser;
    }

    /**
     * 用户脱敏
     * @param user
     * @return
     */
    public User getSafeUser (User user){
        if (user == null) {
            return null;
        }
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setUsername(user.getUsername());
        safeUser.setUserAccount(user.getUserAccount());
        safeUser.setAvatarUrl(user.getAvatarUrl());
        safeUser.setGender(user.getGender());
        safeUser.setPhone(user.getPhone());
        safeUser.setEmail(user.getEmail());
        safeUser.setUserStatus(user.getUserStatus());
        safeUser.setCreateTime(user.getCreateTime());
        safeUser.setUpdateTime(user.getUpdateTime());
        safeUser.setIsDelete(user.getIsDelete());
        safeUser.setUserRole(user.getUserRole());
        safeUser.setUserNumber(user.getUserNumber());
        return safeUser;
    }
}




