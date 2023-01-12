package org.example.dto.parameterDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class IsMemberParameterDto {
    private Integer user;
    private Integer member;
    private Integer club;
}
