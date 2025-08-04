package com.example.StudentsCRUD.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO extends RepresentationModel<ResponseDTO> {
    private int stuId;
    private String stuName;
    private String stuEmail;
    private int deptId;
    private String deptName;
}
