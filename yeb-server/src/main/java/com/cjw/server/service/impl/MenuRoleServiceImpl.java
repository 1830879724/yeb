package com.cjw.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjw.server.mapper.MenuRoleMapper;
import com.cjw.server.pojo.MenuRole;
import com.cjw.server.pojo.RespBean;
import com.cjw.server.service.IMenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cjw
 * @since 2021-07-12
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {
    @Autowired
    private MenuRoleMapper menuRoleMapper;



    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    @Override
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        //删除
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid",rid));
        if (null == mids || 0==mids.length){
            return RespBean.success("更新成功!");
        }
        Integer result = menuRoleMapper.insertRecord(rid,mids);
        if (result==mids.length){
            return  RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败!");
    }
}
