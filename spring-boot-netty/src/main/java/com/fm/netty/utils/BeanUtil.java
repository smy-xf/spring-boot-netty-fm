package com.fm.netty.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

public class BeanUtil {
    /**
     * 对象属性拷贝 <br>
     * 将源对象的属性拷贝到目标对象
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
            BeanUtils.copyProperties(source, target);
    }
}
