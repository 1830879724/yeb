package com.cjw.server;

import com.cjw.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 操作员工类
 */
public class AdminUtils {
    /**
     * 获取当前登录操作员
     * @return
     */
    public   static Admin getCurrentAdmin(){
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
