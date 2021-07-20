package com.cjw.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cjw.server.pojo.Department;

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
}
