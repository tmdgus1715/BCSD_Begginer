package org.example.repository;

import org.example.domain.TrainingLog;
import org.example.dto.parameterDto.GetTrainingLogParameterDto;
import org.example.dto.parameterDto.UpdateParameterDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingLogMapper {
    TrainingLog getTrainingLog(GetTrainingLogParameterDto getTrainingLogParameterDto);

    TrainingLog getTrainingLogById(Integer trainingLog);

    void createTrainingLog(TrainingLog newTrainingLog);

    void updateWeight(UpdateParameterDto updateParameterDto);

    void updateBodypart(UpdateParameterDto updateParameterDto);

    void updateTrainingTime(UpdateParameterDto updateParameterDto);

    List<TrainingLog> getTrainingLogs(Integer userId);

    void deleteTrainingLog(Integer trainingLogId);
}
