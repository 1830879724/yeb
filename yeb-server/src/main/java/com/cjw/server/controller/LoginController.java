package com.cjw.server.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.cjw.server.pojo.Admin;
import com.cjw.server.pojo.AdminLogin;
import com.cjw.server.pojo.RespBean;
import com.cjw.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 登录
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(AdminLogin adminLogin, HttpServletRequest request){
        return  adminService.login(adminLogin.getUsername(),adminLogin.getPassword(),request);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){
        //判断是否为空
        if (null==principal){
            return null;
        }
        //通过principal.getName()获取用户名
        String username=principal.getName();
        //根据用户名获取对象
        Admin admin =adminService.getAdminByUserName(username);
        //用户密码不返回
        admin.setPassword(null);
        return admin;
    }

    @ApiOperation(value = "退出登录!")
    public RespBean logout(){
        return RespBean.success("退出成功");
    }
}
