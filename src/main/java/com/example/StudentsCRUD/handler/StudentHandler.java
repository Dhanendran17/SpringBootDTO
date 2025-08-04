package com.example.StudentsCRUD.handler;

import com.example.StudentsCRUD.dto.RequestDTO;
import com.example.StudentsCRUD.model.Student;
import com.example.StudentsCRUD.repository.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentHandler {

    private final StudentRepo studentRepo;

    public RequestDTO buildRequestDTOFromClone(Integer cloneFromId) {
        Student existing = studentRepo.findById(cloneFromId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID " + cloneFromId));

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setStuName(existing.getStuName());
        String email = existing.getStuEmail();
        if (!email.endsWith("_copy")) {
            email += "_copy";
        }
        requestDTO.setStuEmail(email);
        requestDTO.setDeptId(existing.getDeptId());
        requestDTO.setDeptName(existing.getDeptName());
        requestDTO.setPassword(existing.getPassword());
        return requestDTO;
    }

    public RequestDTO validateOrThrow(RequestDTO dto, Integer cloneFromId) {
        if (dto == null && cloneFromId != null) {
            return buildRequestDTOFromClone(cloneFromId);
        } else if (dto == null) {
            throw new RuntimeException("Either provide a student body or use ?cloneFrom={id}");
        }
        return dto;
    }
}
