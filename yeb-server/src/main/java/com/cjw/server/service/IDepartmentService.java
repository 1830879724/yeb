package com.cjw.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cjw.server.pojo.Department;
import com.cjw.server.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cjw
 * @since 2021-07-12
 */
public interface IDepartmentService extends IService<Department> {
    /**
     * 获取所有部门
     * @return
     */
    List<Department> getAllDepartments();

    /**
     * 添加部门
     * @param department
     * @return
     */
    RespBean addDep(Department department);

    /**
     * 删除部门
     * @param id
     * @return
     */
    RespBean deleteDep(Integer id);
}
