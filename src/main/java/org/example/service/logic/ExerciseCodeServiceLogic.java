package org.example.service.logic;

import lombok.RequiredArgsConstructor;
import org.example.domain.ExerciseCode;
import org.example.dto.ExerciseCodeDto;
import org.example.dto.parameterDto.UpdateParameterDto;
import org.example.repository.ExerciseCodeMapper;
import org.example.service.CheckValidationService;
import org.example.service.ExerciseCodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ExerciseCodeServiceLogic implements ExerciseCodeService {
    private final ExerciseCodeMapper exerciseCodeMapper;
    private final CheckValidationService check;

    @Override
    public void createExerciseCode(ExerciseCodeDto newExerciseCodeDto) {
        check.checkValidationExerciseCode(newExerciseCodeDto);

        ExerciseCode newExerciseCode = newExerciseCodeDto.toEntity();

        exerciseCodeMapper.createExerciseCode(newExerciseCode);
    }

    @Override
    public void updateExerciseCodeName(String exercise, String newExercise) {
        check.checkValidationExerciseCodeName(newExercise);

        ExerciseCode exerciseCode = exerciseCodeMapper.getExerciseCodeByName(exercise);
        exerciseCode.checkValidation();

        UpdateParameterDto exerciseCodeUpdateParameterDto = UpdateParameterDto.builder().id(exerciseCode.getId()).value(newExercise).build();

        exerciseCodeMapper.updateExerciseCodeName(exerciseCodeUpdateParameterDto);
    }

    @Override
    public List<ExerciseCodeDto> getExerciseCodes() {
        List<ExerciseCode> list = exerciseCodeMapper.getExerciseCodes();
        List<ExerciseCodeDto> exerciseCodes = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i) {
            exerciseCodes.add(i, new ExerciseCodeDto(list.get(i)));
        }

        return exerciseCodes;
    }

    @Override
    public void deleteExerciseCode(String exerciseCodeName) {
        ExerciseCode exerciseCode = exerciseCodeMapper.getExerciseCodeByName(exerciseCodeName);
        exerciseCode.checkValidation();

        exerciseCodeMapper.deleteExerciseCode(exerciseCode.getId());
    }
}
