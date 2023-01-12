package org.example.dto.requestDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginUserRequestDto {
    private String loginId;
    private String password;
}
