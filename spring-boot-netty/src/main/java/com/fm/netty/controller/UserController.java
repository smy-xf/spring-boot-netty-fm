package com.fm.netty.controller;

import com.fm.netty.pojo.Users;
import com.fm.netty.pojo.vo.UserVO;
import com.fm.netty.service.UserService;
import com.fm.netty.utils.BeanUtil;
import com.fm.netty.utils.JsonResult;
import com.fm.netty.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @desc 用户信息增删改
 * @author xiaofan
 * @version 1.0.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @desc 登录注册方法
     */
    @PostMapping("/registerOrLogin")
    public JsonResult registerOrLogin(@RequestBody Users users) throws Exception {

        // 0. 判断用户名或密码是否为空
        if (StringUtils.isBlank(users.getUsername()) || StringUtils.isBlank(users.getPassword())) {
            return JsonResult.errorMsg("用户名或密码为空");
        }
        // 1. 判断用户名是否存在，如果存在就登录，如果不存在则注册
        boolean userIsExist = userService.queryUsernameIsExist(users.getUsername());
        Users result = null;
        if (userIsExist) {
            // 1.1 登录
            result = userService.queryUserForLogin(users.getUsername(), MD5Utils.getMD5Str(users.getPassword()));
            if (result == null) {
                return JsonResult.errorMsg("用户名或密码错误");
            }
        } else {
            // 1.2 注册
            result = userService.saveUser(users);
        }

        // 对象属性拷贝
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(result,userVO);

        //返回用户信息
        return JsonResult.ok(userVO);
    }
}
