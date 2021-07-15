package com.cjw.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjw.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cjw
 * @since 2021-07-12
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户id查询角色
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);
}
