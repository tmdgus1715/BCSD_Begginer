package org.example.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.dto.RoutineDto;
import org.example.dto.TrainingLogDto;

import java.util.List;

@Data
@AllArgsConstructor
public class TrainingLogResponseDto {
    private TrainingLogDto trainingLogDto;
    private List<RoutineDto> routineDtos;
}
