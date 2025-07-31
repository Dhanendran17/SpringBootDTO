package com.example.StudentsCRUD.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stu_id")
    private int stuId;

    @Column(name = "stu_name")
    private String stuName; //1

    @Column(name = "stu_email")
    private String stuEmail; //a

    @Column(name = "dept_name")
    private String deptName; //b

    @Column(name = "dept_id")
    private int deptId; //2

    private String password;
}

