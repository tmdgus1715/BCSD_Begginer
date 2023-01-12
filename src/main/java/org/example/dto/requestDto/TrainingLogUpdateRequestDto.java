package org.example.dto.requestDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TrainingLogUpdateRequestDto {
    private String key;
    private String value;
}
