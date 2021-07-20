package com.cjw.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjw.server.pojo.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

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
public interface AdminMapper extends BaseMapper<Admin> {
    /**
     * 获取所有操作员
     * @param id
     * @param keywords
     * @return
     */
    List<Admin> getAllAdmin(@Param("id") Integer id,@Param("keywords") String keywords);
}
