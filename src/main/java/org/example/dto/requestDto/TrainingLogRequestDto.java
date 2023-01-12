package org.example.dto.requestDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.RoutineDto;
import org.example.dto.TrainingLogDto;

import java.util.List;

@Data
@NoArgsConstructor
public class TrainingLogRequestDto {
    private TrainingLogDto trainingLogDto;
    private List<RoutineDto> routineDtos;
}
