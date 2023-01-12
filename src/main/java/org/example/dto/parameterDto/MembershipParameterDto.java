package org.example.dto.parameterDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MembershipParameterDto {
    private Integer userId;
    private Integer clubId;
}
