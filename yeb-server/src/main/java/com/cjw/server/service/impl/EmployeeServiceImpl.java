package com.cjw.server.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjw.server.mapper.EmployeeMapper;
import com.cjw.server.pojo.Employee;
import com.cjw.server.pojo.ResPageBean;
import com.cjw.server.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cjw
 * @since 2021-07-12
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private  EmployeeMapper employeeMapper;


    /**
     *  获取所有员工(分页)
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    @Override
    public ResPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
        //开启分页
        Page<Employee> page =new Page<>(currentPage,size);
        IPage<Employee> employeeIPage= employeeMapper.getEmployeeByPage(page,employee,beginDateScope);
        ResPageBean resPageBean =new ResPageBean(employeeIPage.getTotal(),employeeIPage.getRecords());
        return resPageBean;
    }
}
