package org.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ExerciseCode extends Entity {
    private String exercise;

    public ExerciseCode(String exercise) {
        this.exercise = exercise;
    }

    @Override
    public void checkValidation() {
        if (this == null) throw new NullPointerException("해당 종목은 존재하지 않습니다.");
    }
}
