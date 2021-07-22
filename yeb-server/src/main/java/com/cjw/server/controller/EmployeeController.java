package com.cjw.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.cjw.server.pojo.*;
import com.cjw.server.service.*;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
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

    @ApiOperation(value = "导出员工数据")
    @GetMapping(value = "/export",produces ="application/octet-stream")
    public void exportEmployee(HttpServletResponse response){
        //获取员工数据
        List<Employee> list= employeeService.getEmployee(null);
        //导出数据 导出参数
        ExportParams params = new ExportParams("员工表","员工表", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params,Employee.class,list);
        ServletOutputStream out =null;
        try {
            //设置请求头响应信息  用流形式输出
            response.setHeader("content-type","application/octet-stream");
            //防止中文乱码
            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode("员工表.xls","UTF-8"));
            out= response.getOutputStream();
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (null!=out){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "导入员工数据")
    @PostMapping("/import")
    public RespBean importEmployee(MultipartFile file){
        //准备参数
        ImportParams params=new ImportParams();
        //删除标题
        params.setTitleRows(1);
        //查询民族
         List<Nation> nationList = nationService.list();
        //查询政治面貌
         List<PoliticsStatus> politicsStatusList =politicsStatusService.list();
         //查询部门
        List<Department> departmentList =departmentService.list();
        //查询职称
        List<Joblevel> joblevelList =joblevelService.list();
        //查询职位
        List<Position> positionList =positionService.list();
        try {
           List<Employee> list = ExcelImportUtil.importExcel(file.getInputStream(),Employee.class,params);
           //获取id
            list.forEach(employee -> {
                //通过下标获取民族id
                employee.setNationId(nationList.get(nationList.indexOf(
                        new Nation(employee.getNation()
                                .getName()))).getId());
                //政治面貌id
                employee.setPoliticId(politicsStatusList.get(politicsStatusList.indexOf(
                        new PoliticsStatus(employee.getPoliticsStatus()
                                .getName()))).getId());
                //查询部门id
                employee.setDepartmentId(departmentList.get(departmentList.indexOf(
                        new Department(employee.getDepartment().
                                getName()))).getId());
                //查询职称
                employee.setJobLevelId(joblevelList.get(joblevelList.indexOf(
                        new Joblevel(employee.getJoblevel()
                                .getName()))).getId());
                //查询职位
                employee.setPosId(positionList.get(positionList.indexOf(
                        new Position(employee.getPosition()
                                .getName()))).getId());
            });
            if (employeeService.saveBatch(list)){
                return RespBean.success("导入成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("导入失败!");
    }

}
