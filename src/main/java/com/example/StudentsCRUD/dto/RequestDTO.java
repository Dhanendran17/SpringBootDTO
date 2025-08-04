package com.example.StudentsCRUD.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    private String stuName;
    private String stuEmail;
    private int deptId;
    private String deptName;
    private String password;
}
