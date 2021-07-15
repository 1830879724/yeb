package com.cjw.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjw.server.mapper.MenuMapper;
import com.cjw.server.pojo.Admin;
import com.cjw.server.pojo.Menu;
import com.cjw.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cjw
 * @since 2021-07-14
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 通过用户id查询菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenuByAdminId() {
        Integer adminId =((Admin) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getId();
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        //先去redis取菜单
        List<Menu> list = (List<Menu>) valueOperations.get("menu_" + adminId);
        //判断是否存在
        if (CollectionUtils.isEmpty(list)){
            list=menuMapper.getMenuByAdminId(adminId);
            //将数据设置到redis中
            valueOperations.set("menu_"+adminId,list);
        }
        return list;
    }
}
