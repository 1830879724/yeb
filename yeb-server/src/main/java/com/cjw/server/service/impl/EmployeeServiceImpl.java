package com.cjw.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjw.server.mapper.EmployeeMapper;
import com.cjw.server.pojo.Employee;
import com.cjw.server.pojo.ResPageBean;
import com.cjw.server.pojo.RespBean;
import com.cjw.server.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

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



    /**
     * 获取工号
     * @return
     */
    @Override
    public RespBean maxWorkID() {
           List<Map<String,Object>> maps= employeeMapper.selectMaps(new QueryWrapper<Employee>().
                    select("max(workId)"));
        return RespBean.success(null,String.format("%08d",
                Integer.parseInt( maps.get(0).get("max(workId)").toString())+1));
    }

    /**
     * 添加员工
     * @param employee
     * @return
     */
    @Override
    public RespBean addEmp(Employee employee) {
        //获取合同开始时间
        LocalDate beginContract=employee.getBeginContract();
        //获取合同结束时间
        LocalDate endContract=employee.getEndContract();
        //处理合同期限 计算几天
        long days=beginContract.until(endContract,ChronoUnit.DAYS);
        //保留两位小数
        DecimalFormat decimalFormat=new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days / 365.00)));
        //插入
        if (1 == employeeMapper.insert(employee)){
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败!");
    }
}
