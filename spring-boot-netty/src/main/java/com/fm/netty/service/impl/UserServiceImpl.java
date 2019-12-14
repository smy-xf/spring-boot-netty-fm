package com.fm.netty.service.impl;

import com.fm.netty.mapper.UsersMapper;
import com.fm.netty.pojo.Users;
import com.fm.netty.service.UserService;
import com.fm.netty.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author xiaofan
 * @version 1.0.0
 * @ClassName UserService.java
 * @Description 用户注册、登录Service实现类
 * @createTime 2019/12/14 14:56
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    /**
     * @description 判断用户名是否存在
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        // 0 创建用户对象
        Users user = new Users();
        user.setUsername(username);
        // 1 根据用户名获取用户
        Users result = usersMapper.selectOne(user);
        // 2 判断用户名是否存在，是返回true，否返回false
        return result != null ? true : false;
    }

    /**
     * @Description: 查询用户是否存在
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {
        Example userExample = new Example(Users.class);
        // Criteria是一种比hql更面向对象的查询方式
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", password);
        // 获取用户并返回
        Users result = usersMapper.selectOneByExample(userExample);
        return result;
    }

    /**
     * @Description: 用户保存
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users saveUser(Users user) throws Exception {
        // 为每个用户生成唯一ID
        String userId = sid.nextShort();
        user.setId(userId);
        // 密码
        user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
        // 昵称
        user.setNickname(user.getUsername());
        // 小图标
        user.setFaceImage("");
        // 大图标
        user.setFaceImageBig("");
        // 二维码
        user.setQrcode("");
        //保存用户
        usersMapper.insert(user);
        return user;
    }
}
