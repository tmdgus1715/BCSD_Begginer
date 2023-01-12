package org.example.service;

import org.example.dto.requestDto.TrainingLogRequestDto;
import org.example.dto.responseDto.TrainingLogListDto;
import org.example.dto.responseDto.TrainingLogResponseDto;

import java.util.List;

public interface TrainingLogService {
    void recordTrainingLog(Integer userId, TrainingLogRequestDto newTrainingLog);

    void updateWeight(Integer userId, String date, String value);

    void updateBodypart(Integer userId, String date, String value);

    void updateTrainingTime(Integer userId, String date, String value);

    void deleteTrainingLog(Integer userId, String date);

    TrainingLogResponseDto getMemberTrainingLog(Integer userId, String clubName, String member_loginId, String date);

    List<TrainingLogListDto> getMemberTrainingLogs(Integer userId, String clubName, String member_loginId);

    List<TrainingLogListDto> getMyTrainingLogs(Integer userId);

    TrainingLogResponseDto getMyTrainingLog(Integer userId, String date);
}
