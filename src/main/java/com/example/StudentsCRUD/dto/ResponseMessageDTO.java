package com.example.StudentsCRUD.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessageDTO extends RepresentationModel<ResponseMessageDTO>
{
    private String message;
}
