package com.example.delayedmessageexchange.controller;

import com.example.delayedmessageexchange.entity.Department;
import com.example.delayedmessageexchange.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentController {
    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/department")
    public void saveDepartment(@RequestBody Department department) {
        departmentService.saveDept(department);
    }

    @GetMapping("/department")
    public List<Department> findAllDepartment() {
        return departmentService.findAll();
    }

    @GetMapping("/department/{id}")
    public void findDeptById(@PathVariable int id) {
        departmentService.findDeptById(id);
    }

    @GetMapping("/pushDeptinQueue")
    public void pushInQueue() {
        departmentService.pushMessageToQueue();

    }
//    @GetMapping("/recieveMessage")
//    public void recieveMessage(Department dep){
//        departmentService.recieveMessageFromQueue(dep);
//    }
}
