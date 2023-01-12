package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.ExerciseCode;

@Data
@NoArgsConstructor
public class ExerciseCodeDto {
    private Integer id;
    private String exercise;

    public ExerciseCodeDto(ExerciseCode exerciseCode) {
        this.id = exerciseCode.getId();
        this.exercise = exerciseCode.getExercise();
    }

    public ExerciseCode toEntity() {
        return new ExerciseCode(this.exercise);
    }
}
