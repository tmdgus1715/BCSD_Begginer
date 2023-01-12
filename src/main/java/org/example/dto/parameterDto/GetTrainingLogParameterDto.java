package org.example.dto.parameterDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GetTrainingLogParameterDto {
    private Integer userId;
    private String date;
}
