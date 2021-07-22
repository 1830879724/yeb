package com.cjw.server.controller;


import com.cjw.server.pojo.*;
import com.cjw.server.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cjw
 * @since 2021-07-12
 */
@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IPoliticsStatusService politicsStatusService;

    @Autowired
    private IJoblevelService joblevelService;

    @Autowired
    private INationService nationService;

    @Autowired
    private IPositionService positionService;

    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation(value = "获取所有员工(分页)")
    @GetMapping("/")
    public ResPageBean getEmployee(@RequestParam(defaultValue = "1")Integer currentPage,
                                   @RequestParam(defaultValue = "10")Integer size,
                                   Employee employee, LocalDate[] beginDateScope){
        return employeeService.getEmployeeByPage(currentPage,size,employee,beginDateScope);
    }



    @ApiOperation(value = "获取所有政治面貌")
    @GetMapping("/politicsstayus")
    public List<PoliticsStatus> getAllPoliticsStatus(){
            return politicsStatusService.list();
    }


    @ApiOperation(value = "获取所有职称")
    @GetMapping("/joblevels")
    public List<Joblevel> getAllJoblevel(){
        return joblevelService.list();
    }

    @ApiOperation(value = "获取所有民族")
    @GetMapping("/nations")
    public List<Nation> getAllNation(){
        return nationService.list();
    }

    @ApiOperation(value = "获取所有职位")
    @GetMapping("/position")
    public List<Position> getAllPosition(){
        return positionService.list();
    }


    @ApiOperation(value = "获取所有部门")
    @GetMapping("/department")
    public List<Department> getAllDepartment(){
        return departmentService.getAllDepartments();
    }

    @ApiOperation(value = "获取工号")
    @GetMapping("/maxWorkID")
    public RespBean maxWorkID(){
        return employeeService.maxWorkID();
    }


    @ApiOperation(value = "添加员工")
    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee){
        return employeeService.addEmp(employee);
    }

    @ApiOperation(value = "更新员工")
    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee){
        if (employeeService.updateById(employee)){
            return RespBean.success("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @ApiOperation(value = "删除员工")
    @DeleteMapping("/{id}")
    public  RespBean deleteEmp(@PathVariable Integer id ){
        if (employeeService.removeById(id)){
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");

    }
}
