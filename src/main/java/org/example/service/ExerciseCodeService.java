package org.example.service;

import org.example.dto.ExerciseCodeDto;

import java.util.List;

public interface ExerciseCodeService {
    void createExerciseCode(ExerciseCodeDto newExerciseCode);

    void updateExerciseCodeName(String exerciseCode_name, String value);

    List<ExerciseCodeDto> getExerciseCodes();

    void deleteExerciseCode(String exerciseCode_name);
}
