package com.example.StudentsCRUD.controller;

import com.example.StudentsCRUD.dto.RequestDTO;
import com.example.StudentsCRUD.dto.ResponseDTO;
import com.example.StudentsCRUD.service.UrlStudentService;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlStudentController {

    private UrlStudentService service;

    public UrlStudentController(UrlStudentService service) {
        this.service = service;
    }

    @PostMapping("/addStudent/{stuName}/{stuEmail}/{deptId}/{deptName}/{password}")
    public EntityModel<ResponseDTO> addStudent(
            @PathVariable String stuName,
            @PathVariable String stuEmail,
            @PathVariable int deptId,
            @PathVariable String deptName,
            @PathVariable String password) {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setStuName(stuName);
        requestDTO.setStuEmail(stuEmail);
        requestDTO.setDeptId(deptId);
        requestDTO.setDeptName(deptName);
        requestDTO.setPassword(password);
        return service.addStudent(requestDTO);
    }

    @PutMapping("/updateStudent/{sId}/{stuName}/{stuEmail}/{deptId}/{deptName}/{password}")
    public EntityModel<ResponseDTO> updateStudent(
            @PathVariable int sId,
            @PathVariable String stuName,
            @PathVariable String stuEmail,
            @PathVariable int deptId,
            @PathVariable String deptName,
            @PathVariable String password) {

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setStuName(stuName);
        requestDTO.setStuEmail(stuEmail);
        requestDTO.setDeptId(deptId);
        requestDTO.setDeptName(deptName);
        requestDTO.setPassword(password);

        return service.updateStudent(sId, requestDTO);
    }
}
