package org.example.domain;

import lombok.*;


@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TrainingLog extends Entity {
    private Integer user;
    private String date;
    private byte weight;
    private String bodypart;
    private short training_Time;
    private byte validate;

    @Builder
    private TrainingLog(Integer user, String date, byte weight, String bodypart, short training_Time) {
        this.date = date;
        this.weight = weight;
        this.bodypart = bodypart;
        this.training_Time = training_Time;

    }

    public void setTrainingLogInit(Integer user) {
        this.user = user;
        this.validate = 1;
    }

    @Override
    public void checkValidation() {
        if (this == null || validate == 0) throw new NullPointerException("해당 운동일지는 존재하지 않습니다.");
    }
}
