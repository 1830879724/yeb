package com.cjw.server.config.security.component;

import com.cjw.server.pojo.Menu;
import com.cjw.server.pojo.Role;
import com.cjw.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.ConfigAttribute;

import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 根据url分析请求的角色
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private IMenuService menuService;
    AntPathMatcher antPathMatcher =new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object obj) throws IllegalArgumentException {
        //获取请求的url
        String requestUrl= ((FilterInvocation) obj).getRequestUrl();
        //根据角色获取菜单列表
        List<Menu> menus = menuService.getMenusWithRole();
        for (Menu menu : menus) {
            //判断请求url与菜单角色是否匹配
            if (antPathMatcher.match(menu.getUrl(),requestUrl)){
            String[] str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
           return SecurityConfig.createList(str);
            }
        }
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
