package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.Routine;

@Data
@NoArgsConstructor
public class RoutineDto {
    private Integer exercise;
    private int weight;
    private short reps;
    private short set;
    private int recess;
    private byte rpe;

    public RoutineDto(Routine routine) {
        this.exercise = routine.getExercise();
        this.weight = routine.getWeight();
        this.reps = routine.getReps();
        this.set = routine.getSet();
        this.recess = routine.getRecess();
        this.rpe = routine.getRpe();
    }

    public Routine toEntity() {
        return Routine.builder()
                .exercise(exercise)
                .weight(weight)
                .reps(reps)
                .set(set)
                .recess(recess)
                .rpe(rpe)
                .build();
    }
}

