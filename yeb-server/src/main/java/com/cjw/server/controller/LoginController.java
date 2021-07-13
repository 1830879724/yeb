package com.cjw.server.controller;

import com.cjw.server.pojo.AdminLogin;
import com.cjw.server.pojo.RespBean;
import com.cjw.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
}
