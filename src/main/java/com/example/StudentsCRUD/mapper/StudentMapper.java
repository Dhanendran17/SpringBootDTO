package com.example.StudentsCRUD.mapper;

import com.example.StudentsCRUD.dto.RequestDTO;
import com.example.StudentsCRUD.dto.ResponseDTO;
import com.example.StudentsCRUD.model.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper
{
    //Request DTO to Entity
    public Student toEntity(RequestDTO requestDTO)
    {
        Student student = new Student();
        student.setStuName(requestDTO.getStuName());
        student.setStuEmail(requestDTO.getStuEmail());
        student.setDeptId(requestDTO.getDeptId());
        student.setPassword(requestDTO.getPassword());
        student.setDeptName(requestDTO.getDeptName());
        return student;
    }

    //Response Entity to DTO
    public static ResponseDTO toDTO(Student student, String deptName)
    {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStuId(student.getStuId());
        responseDTO.setStuName(student.getStuName());
        responseDTO.setStuEmail(student.getStuEmail());
        responseDTO.setDeptId(student.getDeptId());
        responseDTO.setDeptName(deptName);
        return  responseDTO;
    }
}
