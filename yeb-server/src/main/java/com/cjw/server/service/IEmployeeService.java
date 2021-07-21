package com.cjw.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cjw.server.pojo.Employee;
import com.cjw.server.pojo.ResPageBean;
import com.cjw.server.pojo.RespBean;

import java.time.LocalDate;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cjw
 * @since 2021-07-12
 */
public interface IEmployeeService extends IService<Employee> {


    /**
     * 获取所有员工(分页)
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    ResPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    /**
     * 获取工号
     * @return
     */
    RespBean maxWorkID();

    /**
     * 添加员工
     * @param employee
     * @return
     */
    RespBean addEmp(Employee employee);
}
