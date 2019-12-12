package com.fm.netty.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

public class MD5Utils {

    /**
     * @ClassName MD5Utils.java
     * @author xiaofan
     * @version 1.0.0
     * @Description 对字符串进行md5加密
     * @createTime 2019/12/12 22:00
     */
    public static String getMD5Str(String strValue) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String newstr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
        return newstr;
    }

    public static void main(String[] args) {
        try {
            String md5 = getMD5Str("imooc");
            System.out.println(md5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
