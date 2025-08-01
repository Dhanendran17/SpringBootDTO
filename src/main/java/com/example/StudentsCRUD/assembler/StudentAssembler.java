package com.example.StudentsCRUD.assembler;

import com.example.StudentsCRUD.controller.StudentController;
import com.example.StudentsCRUD.dto.RequestDTO;
import com.example.StudentsCRUD.dto.ResponseDTO;
import com.example.StudentsCRUD.mapper.StudentMapper;
import com.example.StudentsCRUD.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class StudentAssembler implements RepresentationModelAssembler<Student, EntityModel<ResponseDTO>> {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public EntityModel<ResponseDTO> toModel(Student student) {
        ResponseDTO dto = studentMapper.toDTO(student, student.getDeptName());

        return EntityModel.of(dto,
                linkTo(methodOn(StudentController.class)
                        .getStudentById(dto.getStuId()))
                        .withSelfRel()
                        .withType("GET"),

                linkTo(methodOn(StudentController.class)
                        .getAllStudents(0, 2, "stuName", "asc"))
                        .withRel("allStudents")
                        .withType("GET"),

                linkTo(methodOn(StudentController.class)
                        .addStudent(null))
                        .withRel("addStudent")
                        .withType("POST"),

                linkTo(methodOn(StudentController.class)
                        .updateStudent(dto.getStuId(), new RequestDTO()))
                        .withRel("updateStudent")
                        .withType("PUT"),
                linkTo(methodOn(StudentController.class)
                        .deleteStudent(dto.getStuId(), null))
                        .withRel("deleteStudent")
                        .withType("DELETE")
        );
    }
}
