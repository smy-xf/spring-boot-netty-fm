package com.fm.netty.controller;

import com.fm.netty.pojo.Users;
import com.fm.netty.pojo.bo.UserBo;
import com.fm.netty.pojo.vo.UserVO;
import com.fm.netty.service.UserService;
import com.fm.netty.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @desc 用户信息增删改 测试一下
 * @author xiaofan
 * @version 1.0.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FastDfcClient fastDfcClient;
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

    /**
     * @desc 登录注册方法
     */
    @PostMapping("/uploadFaceBase64")
    public JsonResult uploadFaceBase64(@RequestBody UserBo userBo) throws Exception {
        // 获取前端传过来的base64字符串, 然后转换为文件对象再上传
        String base64Data = userBo.getFaceData();
        String filePath = "C:\\" + userBo.getUserId() +"userface64.png";
        FileUtils.base64ToFile(filePath,base64Data);

        // 上传文件到fastdfs
        MultipartFile multipartFile = FileUtils.fileToMultipart(filePath);
        String userFaceUrl = fastDfcClient.uploadBase64(multipartFile);
        System.out.println(userFaceUrl);

        // 获取缩略图的url，fastDfc默认会对缩略图拼接 ，例如： "123.png"  ---》 "123_80x80.png"
        String thump = "_80x80.";
        String[] arr = userFaceUrl.split("\\.");
        String thumpUrl = arr[0] + thump + arr[1];

        // 更新用户头像
        Users user = new Users();
        user.setId(userBo.getUserId());
        user.setFaceImageBig(userFaceUrl);
        user.setFaceImage(thumpUrl);

        Users result  = userService.updateUserInfo(user);
        return JsonResult.ok(result);
    }
}
