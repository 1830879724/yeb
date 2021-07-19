package com.cjw.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjw.server.pojo.MenuRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cjw
 * @since 2021-07-12
 */
@Repository
@Component
public interface MenuRoleMapper extends BaseMapper<MenuRole> {
    /**
     * 批量更新
     * @param rid
     * @param mids
     */
    Integer insertRecord(@Param("rid") Integer rid,@Param("mids") Integer[] mids);
}
