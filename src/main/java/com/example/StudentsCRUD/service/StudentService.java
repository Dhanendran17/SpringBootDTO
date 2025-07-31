package com.example.StudentsCRUD.service;

import com.example.StudentsCRUD.assembler.StudentAssembler;
import com.example.StudentsCRUD.dto.PaginatedResponse;
import com.example.StudentsCRUD.dto.RequestDTO;
import com.example.StudentsCRUD.dto.ResponseDTO;
import com.example.StudentsCRUD.controller.StudentController;
import com.example.StudentsCRUD.dto.ResponseMessageDTO;
import com.example.StudentsCRUD.mapper.StudentMapper;
import com.example.StudentsCRUD.model.Student;
import com.example.StudentsCRUD.repository.StudentRepo;
import org.springframework.data.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentAssembler studentAssembler;

    //Get all Student list
    public PagedModel<EntityModel<ResponseDTO>> getAllStudents(int page, int size, String sortField, String sortDirection, PagedResourcesAssembler<ResponseDTO> pagedResourcesAssembler)
    {
        //Normal DTO
        /*return studentRepo.findAll()
                .stream()
                .map(student -> studentMapper.toDTO(student, student.getDeptName()))
                .collect(Collectors.toList());*/

        //DTO using HATEOAS
        /*List<Student> students = studentRepo.findAll();
        List<ResponseDTO> dtos = new ArrayList<>();
        for (Student student : students) {
            ResponseDTO dto = studentMapper.toDTO(student, student.getDeptName());

            dto.add(linkTo(StudentController.class)
                    .slash("addStudent")
                    .withRel("addStudent")
                    .withType("POST"));

            dto.add(linkTo(methodOn(StudentController.class)
                    .getStudentById(student.getStuId()))
                    .withSelfRel()
                    .withType("GET"));

            dtos.add(dto);
        }
        return dtos;*/

        //Pagination
        /*Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Student> studentPage = studentRepo.findAll(pageable);

        List<ResponseDTO> dtoList = studentPage.stream()
                .map(student ->{
                    ResponseDTO dto = studentMapper.toDTO(student, student.getDeptName());
                    dto.add(linkTo(StudentController.class)
                            .slash("addStudent")
                            .withRel("addStudent")
                            .withType("POST"));

                    dto.add(linkTo(methodOn(StudentController.class)
                            .getStudentById(student.getStuId()))
                            .withSelfRel()
                            .withType("GET"));
                    return dto;
                })
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                dtoList,
                studentPage.getNumber(),
                studentPage.getSize(),
                studentPage.getTotalElements(),
                studentPage.getTotalPages(),
                studentPage.isLast()
        );*/

        //Resourced Assembler
        /*Sort sort =  sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Student> studentPage = studentRepo.findAll(pageable);

        return studentPage.map(student -> {
            ResponseDTO dto = studentMapper.toDTO(student, student.getDeptName());
            return studentAssembler.toModel(dto);
        });*/

        //PagedResourceAssembler
        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Student> studentPage = studentRepo.findAll(pageable); //1
        Page<ResponseDTO> dtoPage = studentPage.map(student ->
                studentMapper.toDTO(student, student.getDeptName())  //2  //3 URL
        );
        return pagedResourcesAssembler.toModel(dtoPage, studentAssembler);
    }

    //Get the particular student list by Id
    public EntityModel<ResponseDTO> getStudentById(int s_id) {

        //Normal DTO
        /*Student student = studentRepo.findById(s_id).orElseThrow(() -> new RuntimeException("Student not found"));
        return studentMapper.toDTO(student, student.getDeptName());*/

        //DTO using HATEOAS
       /* Student student = studentRepo.findById(s_id).orElseThrow(() -> new RuntimeException("Not found"));
        ResponseDTO dto = studentMapper.toDTO(student, student.getDeptName());

        dto.add(linkTo(methodOn(StudentController.class)
                .getAllStudents(0,2,"stuName","asc"))
                .withRel("allStudents")
                .withType("GET"));

        dto.add(linkTo(StudentController.class)
                .slash("addStudent")
                .withRel("addStudent")
                .withType("POST"));

        return dto;*/

        //Resourse assembler
        Student student = studentRepo.findById(s_id).orElseThrow(() -> new RuntimeException("Student not found"));
        ResponseDTO dto = studentMapper.toDTO(student, student.getDeptName());
        return studentAssembler.toModel(dto);
    }

    //Add the student in the list
    public EntityModel<ResponseDTO> addStudent(RequestDTO requestDTO) {

        //Normal DTO
        /*Student student = studentMapper.toEntity(requestDTO);
        Student saved =  studentRepo.save(student);
        ResponseDTO dto = studentMapper.toDTO(saved, saved.getDeptName());

        //DTO using HATEOAS
        dto.add(linkTo(methodOn(StudentController.class)
                .getStudentById(saved.getStuId()))
                .withRel("studentById")
                .withType("GET"));

        dto.add(linkTo(methodOn(StudentController.class)
                .getAllStudents(0,2,"stuName","asc"))
                .withRel("allStudents")
                .withType("GET"));

        dto.add(linkTo(methodOn(StudentController.class)
                .updateStudent(saved.getStuId(), new RequestDTO()))
                .withRel("updateStudent")
                .withType("PUT"));

        dto.add(linkTo(methodOn(StudentController.class)
                .deleteStudent(saved.getStuId()))
                .withRel("deleteStudentById")
                .withType("DELETE"));

        return dto;
        //return studentMapper.toDTO(saved, saved.getDeptName());*/

        //Resource Assembler
        Student student = studentMapper.toEntity(requestDTO);
        Student saved =  studentRepo.save(student);
        ResponseDTO dto = studentMapper.toDTO(saved, saved.getDeptName());
        return studentAssembler.toModel(dto);

    }

    //Update the student by Id
    public EntityModel<ResponseDTO> updateStudent(int sId, RequestDTO requestDTO) {

        //DTO Using HATEOAS
        Optional<Student> optionalStudent = studentRepo.findById(sId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();

            student.setStuName(requestDTO.getStuName());
            student.setStuEmail(requestDTO.getStuEmail());
            student.setDeptId(requestDTO.getDeptId());
            student.setDeptName(requestDTO.getDeptName());
            student.setPassword(requestDTO.getPassword());

            Student updated = studentRepo.save(student);
            ResponseDTO dto = studentMapper.toDTO(updated, updated.getDeptName());

            /*dto.add(linkTo(methodOn(StudentController.class)
                    .getStudentById(sId))
                    .withSelfRel()
                    .withType("GET"));

            dto.add(linkTo(methodOn(StudentController.class)
                    .deleteStudent(sId))
                    .withRel("deleteStudent")
                    .withType("DELETE"));

            return dto;*/
            return studentAssembler.toModel(dto);
        }
        else {
            throw new RuntimeException("Student with ID " + sId + " not found");
        }
    }

    //Delete the student by Id
    public ResponseMessageDTO deleteStudentById(int sId, PagedResourcesAssembler<ResponseDTO> pagedResourcesAssembler) {
        if(!studentRepo.findById(sId).isPresent()) {
            throw new RuntimeException("Student with ID " + sId + " not found");
        }
        studentRepo.deleteById(sId);
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO("Student deleted successfully");

        responseMessageDTO.add(linkTo(methodOn(StudentController.class)
                .getAllStudents(0,2,"stuName","asc",pagedResourcesAssembler))
                .withRel("allStudents")
                .withType("GET"));


        responseMessageDTO.add(linkTo(methodOn(StudentController.class)
                .addStudent(new RequestDTO()))
                .withRel("addStudent")
                .withType("POST"));

        return responseMessageDTO;
    }

    //Delete all the students in the list
    public ResponseMessageDTO deleteAll() {
        studentRepo.deleteAll();
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO("All students deleted");

        responseMessageDTO.add(linkTo(methodOn(StudentController.class)
                .addStudent(new RequestDTO()))
                .withRel("addStudent")
                .withType("POST"));

        return responseMessageDTO;
    }
}


