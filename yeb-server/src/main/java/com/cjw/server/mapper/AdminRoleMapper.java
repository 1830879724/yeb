package com.cjw.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjw.server.pojo.AdminRole;
import com.cjw.server.pojo.RespBean;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cjw
 * @since 2021-07-12
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {
    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    Integer addAdminRole(@Param("adminId") Integer adminId,@Param("rids") Integer[] rids);
}
