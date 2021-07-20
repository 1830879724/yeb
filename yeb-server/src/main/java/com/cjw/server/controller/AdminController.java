package com.cjw.server.controller;


import com.cjw.server.pojo.Admin;
import com.cjw.server.pojo.RespBean;
import com.cjw.server.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cjw
 * @since 2021-07-12
 */
@RestController
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/")
    public List<Admin> getAllAdmin(String keywords){
        return adminService.getAllAdmin(keywords);
    }

    @ApiOperation(value = "更新操作员")
    @PutMapping ("/")
    public RespBean updateAdmin(@RequestBody Admin admin){
        if (adminService.updateById(admin)){
            return RespBean.success("更新成功!");
        }
        return  RespBean.error("更新失败!");
    }

    @ApiOperation(value = "删除操作员")
    @DeleteMapping("/{id}")
    public RespBean deleteAdmin(@PathVariable Integer id){
        if (adminService.removeById(id)){
            return RespBean.success("删除成功！");
    }
        return RespBean.error("删除失败!");

    }



}
