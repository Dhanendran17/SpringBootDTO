package com.example.StudentsCRUD.service;

import com.example.StudentsCRUD.assembler.StudentAssembler;
import com.example.StudentsCRUD.dto.RequestDTO;
import com.example.StudentsCRUD.dto.ResponseDTO;
import com.example.StudentsCRUD.mapper.StudentMapper;
import com.example.StudentsCRUD.model.Student;
import com.example.StudentsCRUD.repository.StudentRepo;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlStudentService {

    @Autowired
    private StudentMapper  studentMapper;

    @Autowired
    private StudentRepo  studentRepo;

    @Autowired
    private StudentAssembler  studentAssembler;
    public EntityModel<ResponseDTO> addStudent(RequestDTO requestDTO) {
        Student student = new Student();
        student.setStuName(requestDTO.getStuName());
        student.setStuEmail(requestDTO.getStuEmail());
        student.setDeptId(requestDTO.getDeptId());
        student.setDeptName(requestDTO.getDeptName());
        student.setPassword(requestDTO.getPassword());

        Student saved = studentRepo.save(student);
        return studentAssembler.toModel(saved);
    }

    public EntityModel<ResponseDTO> updateStudent(int sId, RequestDTO requestDTO) {
        Optional<Student> optionalStudent = studentRepo.findById(sId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setStuName(requestDTO.getStuName());
            student.setStuEmail(requestDTO.getStuEmail());
            student.setDeptId(requestDTO.getDeptId());
            student.setDeptName(requestDTO.getDeptName());
            student.setPassword(requestDTO.getPassword());

            Student updated = studentRepo.save(student);
            return studentAssembler.toModel(updated);
        } else {
            throw new RuntimeException("Student with ID " + sId + " not found");
        }
    }
}
