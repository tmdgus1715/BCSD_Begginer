package org.example.repository;

import org.example.domain.ExerciseCode;
import org.example.dto.parameterDto.UpdateParameterDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseCodeMapper {
    ExerciseCode getExerciseCodeByName(String exercise);

    void createExerciseCode(ExerciseCode newExerciseCode);

    void updateExerciseCodeName(UpdateParameterDto exerciseCodeUpdateParameterDto);

    List<ExerciseCode> getExerciseCodes();

    void deleteExerciseCode(Integer id);

    ExerciseCode getExerciseCode(Integer exercise);
}
