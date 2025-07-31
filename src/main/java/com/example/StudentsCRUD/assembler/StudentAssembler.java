package com.example.StudentsCRUD.assembler;

import com.example.StudentsCRUD.controller.StudentController;
import com.example.StudentsCRUD.dto.RequestDTO;
import com.example.StudentsCRUD.dto.ResponseDTO;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class StudentAssembler implements RepresentationModelAssembler<ResponseDTO, EntityModel<ResponseDTO>> {

    @Override
    public EntityModel<ResponseDTO> toModel(ResponseDTO dto)
    {
        return EntityModel.of(dto,
                linkTo(methodOn(StudentController.class).
                        getStudentById(dto.getStuId()))
                        .withSelfRel()
                        .withType("GET"),

                linkTo(methodOn(StudentController.class)
                        .getAllStudents(0, 2, "stuName", "asc",null))
                        .withRel("allStudents")
                        .withType("GET"),

                linkTo(methodOn(StudentController.class).
                        addStudent(null))
                        .withRel("addStudent")
                        .withType("POST"),

                linkTo(methodOn(StudentController.class)
                        .updateStudent(dto.getStuId(), new RequestDTO()))
                        .withRel("updateStudent")
                        .withType("PUT"),

                linkTo(methodOn(StudentController.class)
                        .deleteStudent(dto.getStuId(),null))
                        .withRel("deleteStudent")
                        .withType("DELETE")
        );
    }
}
