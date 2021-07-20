package com.cjw.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjw.server.pojo.Department;
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
public interface DepartmentMapper extends BaseMapper<Department> {
    /**
     * 获取所有部门
     * @return
     */
    List<Department> getAllDepartments(Integer parentId);

    /**
     * 添加部门
     * @param department
     */
    void addDep(Department department);
}
