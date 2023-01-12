package org.example.domain;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Routine extends Entity {

    private Integer exercise;
    private int weight;
    private short reps;
    private short set;
    private int recess;
    private byte rpe;
    private byte validate;
    private Integer training_Log;

    @Builder
    private Routine(Integer exercise, int weight, short reps, short set, int recess, byte rpe) {
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        this.set = set;
        this.recess = recess;
        this.rpe = rpe;
    }

    public void setRoutineInit(Integer training_Log) {
        this.validate = 1;
        this.training_Log = training_Log;
    }

    @Override
    public void checkValidation() {
        if (this == null || validate == 0) throw new NullPointerException("해당 루틴은 존재하지 않습니다.");
    }
}
