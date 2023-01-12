package org.example.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.BoardDto;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class BoardResponseDto {
    private TrainingLogResponseDto trainingLogResponseDto;
    private BoardDto boardDto;
}
