package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.TrainingLog;

@Data
@NoArgsConstructor
public class TrainingLogDto {
    private String date;
    private byte weight;
    private String bodypart;
    private short trainingTime;

    public TrainingLogDto(TrainingLog trainingLog) {
        this.date = trainingLog.getDate();
        this.weight = trainingLog.getWeight();
        this.bodypart = trainingLog.getBodypart();
        this.trainingTime = trainingLog.getTraining_Time();
    }

    public TrainingLog toEntity() {
        return TrainingLog.builder()
                .date(date)
                .training_Time(trainingTime)
                .bodypart(bodypart)
                .weight(weight)
                .build();
    }
}
