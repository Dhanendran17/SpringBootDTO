package com.example.StudentsCRUD.controller;

import com.example.StudentsCRUD.dto.PaginatedResponse;
import com.example.StudentsCRUD.dto.RequestDTO;
import com.example.StudentsCRUD.dto.ResponseDTO;
import com.example.StudentsCRUD.dto.ResponseMessageDTO;
import com.example.StudentsCRUD.model.Student;
import com.example.StudentsCRUD.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class StudentController
{
    @Autowired
    private final StudentService service;
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/students")
    public PagedModel<EntityModel<ResponseDTO>> getAllStudents(
            @PageableDefault(sort = "stuName", direction = Sort.Direction.ASC) Pageable pageable,
            PagedResourcesAssembler<Student> pagedResourcesAssembler
    ) {
        return service.getAllStudents(pageable, pagedResourcesAssembler);
    }

    @GetMapping("/students/{s_id}")
    public EntityModel<ResponseDTO> getStudentById(@PathVariable int s_id) {
        return service.getStudentById(s_id);
    }

    /*@PostMapping("/addStudent")
    public EntityModel<ResponseDTO> addStudent(@RequestBody RequestDTO requestDTO) {
        return service.addStudent(requestDTO);
    }*/
    @PostMapping("/addStudent")
    public EntityModel<ResponseDTO> addStudent(
            @RequestBody(required = false) RequestDTO requestDTO,
            @RequestParam(value = "cloneFrom", required = false) Integer cloneFromId
    ) {
        return service.addStudent(requestDTO, cloneFromId);
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
