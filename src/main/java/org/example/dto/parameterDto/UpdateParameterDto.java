package org.example.dto.parameterDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UpdateParameterDto {
    private Integer id;
    private String value;
    private Integer integerValue;
}
