package com.example.StudentsCRUD.controller;

import com.example.StudentsCRUD.dto.PaginatedResponse;
import com.example.StudentsCRUD.dto.RequestDTO;
import com.example.StudentsCRUD.dto.ResponseDTO;
import com.example.StudentsCRUD.dto.ResponseMessageDTO;
import com.example.StudentsCRUD.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
public class StudentController
{
    @Autowired
    private StudentService service;

    @GetMapping("/students")
    public PagedModel<EntityModel<ResponseDTO>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "stuName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            PagedResourcesAssembler<ResponseDTO> pagedResourcesAssembler) {

        return service.getAllStudents(page, size, sortBy, direction, pagedResourcesAssembler);
    }

    @GetMapping("/students/{s_id}")
    public EntityModel<ResponseDTO> getStudentById(@PathVariable int s_id) {
        return service.getStudentById(s_id);
    }

    @PostMapping("/addStudent")
    public EntityModel<ResponseDTO> addStudent(@RequestBody RequestDTO requestDTO) {
        return service.addStudent(requestDTO);
    }

    @PutMapping("/updateStudent/{sId}")
    public EntityModel<ResponseDTO> updateStudent(@PathVariable int sId, @RequestBody RequestDTO requestDTO) {
        return service.updateStudent(sId, requestDTO);
    }

    @DeleteMapping("/students/{id}")
    public ResponseMessageDTO deleteStudent(
            @PathVariable int id,
            PagedResourcesAssembler<ResponseDTO> pagedResourcesAssembler) {
        return service.deleteStudentById(id, pagedResourcesAssembler);
    }

    @DeleteMapping("/deleteAll")
    public ResponseMessageDTO deleteAll() {
        return service.deleteAll();
    }
}
