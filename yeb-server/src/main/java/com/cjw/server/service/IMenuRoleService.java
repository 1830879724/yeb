package com.cjw.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cjw.server.pojo.MenuRole;
import com.cjw.server.pojo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cjw
 * @since 2021-07-12
 */
public interface IMenuRoleService extends IService<MenuRole> {
    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    RespBean updateMenuRole(Integer rid, Integer[] mids);
}
