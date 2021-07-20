package com.cjw.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjw.server.mapper.DepartmentMapper;
import com.cjw.server.pojo.Department;
import com.cjw.server.pojo.RespBean;
import com.cjw.server.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cjw
 * @since 2021-07-12
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

   @Autowired
    private DepartmentMapper departmentMapper;



    /**
     * 获取所有部门
     * @return
     */
    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartments(-1);
    }

    /**
     * 添加部门
     * @param department
     * @return
     */
    @Override
    public RespBean addDep(Department department) {
        department.setEnabled(true);
        //调用存储过程
        departmentMapper.addDep(department);
        if (1==department.getResult()){
            return RespBean.success("添加成功",department);
        }
        return RespBean.error("添加失败",department);
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @Override
    public RespBean deleteDep(Integer id) {
        Department department=new Department();
        department.setId(id);
        departmentMapper.deleteDep(department);
        if (-2==department.getResult()){
            return RespBean.error("该部门下还有子部门，删除失败!");
        }
        if (-1==department.getResult()){
            return RespBean.error("该部门下还有员工，删除失败!");
        }
        if (1==department.getResult()){
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
