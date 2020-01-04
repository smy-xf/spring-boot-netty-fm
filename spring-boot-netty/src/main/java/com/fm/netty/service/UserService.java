package com.fm.netty.service;

import com.fm.netty.pojo.Users;

/**
 * @author xiaofan
 * @version 1.0.0
 * @ClassName UserService.java
 * @Description 用户注册、登录Service类
 * @createTime 2019/12/14 14:56
 */
public interface UserService {

    /**
     * @Description: 查询用户名是否存在
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * @Description: 查询用户是否存在
     */
    public Users queryUserForLogin(String username, String password);


    /**
     * @Description: 用户保存
     */
    public Users saveUser(Users users) throws Exception;

    /**
     * @Description: 用户更新
     */
    public Users updateUserInfo(Users user);
}
