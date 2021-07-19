package com.cjw.server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjw.server.pojo.Menu;
import com.cjw.server.pojo.MenuRole;
import com.cjw.server.pojo.RespBean;
import com.cjw.server.pojo.Role;
import com.cjw.server.service.IMenuRoleService;
import com.cjw.server.service.IMenuService;
import com.cjw.server.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限组
 */
@RestController
@RequestMapping("system/basic/permiss")
public class PermissionController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IMenuRoleService menuRoleService;
    @ApiOperation(value = "获取所有角色")
    @GetMapping("/")
    public List<Role> getAllRole(){
        return roleService.list();
    }


    @ApiOperation(value = "添加角色")
    @PostMapping("/")
    public RespBean addRole(@RequestBody Role role){
        if (!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        if (roleService.save(role)){
            return  RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }


    @ApiOperation(value =  "删除角色")
    @DeleteMapping("/role/{rid}")
    public  RespBean deleteRole(@PathVariable Integer rid){
        if (roleService.removeById(rid)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");

    }

    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/menus")
    public List<Menu> getAllMenus(){
        return  menuService.getAllMenus();
    }

    @ApiOperation(value = "根据用户id查询菜单id")
    @GetMapping("/mid/{rid}")
    public List<Integer> getMidByRid(@PathVariable Integer rid){
        return  menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid",rid))
                .stream().map(MenuRole::getMid).collect(Collectors.toList());
    }
}
