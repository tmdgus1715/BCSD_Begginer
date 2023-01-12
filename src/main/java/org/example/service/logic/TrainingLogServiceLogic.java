package org.example.service.logic;

import lombok.RequiredArgsConstructor;
import org.example.domain.Club;
import org.example.domain.Routine;
import org.example.domain.TrainingLog;
import org.example.domain.User;
import org.example.dto.RoutineDto;
import org.example.dto.TrainingLogDto;
import org.example.dto.parameterDto.GetTrainingLogParameterDto;
import org.example.dto.parameterDto.UpdateParameterDto;
import org.example.dto.requestDto.TrainingLogRequestDto;
import org.example.dto.responseDto.TrainingLogListDto;
import org.example.dto.responseDto.TrainingLogResponseDto;
import org.example.repository.ClubMapper;
import org.example.repository.RoutineMapper;
import org.example.repository.TrainingLogMapper;
import org.example.repository.UserMapper;
import org.example.service.CheckValidationService;
import org.example.service.TrainingLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class TrainingLogServiceLogic implements TrainingLogService {
    private final UserMapper userMapper;
    private final TrainingLogMapper trainingLogMapper;
    private final RoutineMapper routineMapper;
    private final ClubMapper clubMapper;
    private final CheckValidationService check;

    @Override
    public void recordTrainingLog(Integer userId, TrainingLogRequestDto trainingLogRequestDto) {
        User user = userMapper.getUser(userId);
        user.checkValidation();

        TrainingLogDto trainingLogDto = trainingLogRequestDto.getTrainingLogDto();
        check.checkValidationTrainingLog(trainingLogDto, user.getId());

        TrainingLog newTrainingLog = trainingLogDto.toEntity();
        newTrainingLog.setTrainingLogInit(user.getId());

        trainingLogMapper.createTrainingLog(newTrainingLog);

        TrainingLog trainingLog = trainingLogMapper.getTrainingLog(new GetTrainingLogParameterDto(user.getId(), newTrainingLog.getDate()));
        List<RoutineDto> list = trainingLogRequestDto.getRoutineDtos();
        for (int i = 0; i < list.size(); ++i) {
            RoutineDto element = list.get(i);

            check.checkValidationRoutine(element);

            Routine newRoutine = element.toEntity();
            newRoutine.setRoutineInit(trainingLog.getId());

            routineMapper.createRoutine(newRoutine);
        }
    }

    @Override
    public void updateWeight(Integer userId, String date, String newWeight) {
        check.checkValidationTrainingLogWeight(Byte.parseByte(newWeight));

        User user = userMapper.getUser(userId);
        user.checkValidation();

        TrainingLog trainingLog = trainingLogMapper.getTrainingLog(new GetTrainingLogParameterDto(user.getId(), date));
        trainingLog.checkValidation();

        trainingLogMapper.updateWeight(
                UpdateParameterDto
                        .builder()
                        .id(trainingLog.getId())
                        .integerValue(Integer.parseInt(newWeight))
                        .build());
    }

    @Override
    public void updateBodypart(Integer userId, String date, String newBodypart) {
        check.checkValidationTrainingLogBodypart(newBodypart);

        User user = userMapper.getUser(userId);
        user.checkValidation();

        TrainingLog trainingLog = trainingLogMapper.getTrainingLog(new GetTrainingLogParameterDto(user.getId(), date));
        trainingLog.checkValidation();

        trainingLogMapper.updateBodypart(
                UpdateParameterDto
                        .builder()
                        .id(trainingLog.getId())
                        .value(newBodypart)
                        .build());
    }

    @Override
    public void updateTrainingTime(Integer userId, String date, String newTrainingTime) {
        check.checkValidationTrainingLogTrainingTime(Short.parseShort(newTrainingTime));

        User user = userMapper.getUser(userId);
        user.checkValidation();

        TrainingLog trainingLog = trainingLogMapper.getTrainingLog(new GetTrainingLogParameterDto(user.getId(), date));
        trainingLog.checkValidation();

        trainingLogMapper.updateTrainingTime(
                UpdateParameterDto.builder()
                        .id(trainingLog.getId())
                        .integerValue(Integer.parseInt(newTrainingTime))
                        .build()
        );
    }

    @Override//Response와 Request로 나눈 이유는 나중에 추가로 Response에 volume같은 기능을 추가하기 위함.
    public TrainingLogResponseDto getMemberTrainingLog(Integer userId, String clubName, String memberLoginId, String date) {
        User user = userMapper.getUser(userId);
        user.checkValidation();

        User member = userMapper.getUserByLoginId(memberLoginId);
        member.checkValidation();

        Club club = clubMapper.getClubByName(clubName);
        club.checkValidation();

        check.isMember(user.getId(), member.getId(), club.getId());

        TrainingLog trainingLog = trainingLogMapper.getTrainingLog(new GetTrainingLogParameterDto(member.getId(), date));
        trainingLog.checkValidation();

        List<Routine> list = routineMapper.getRoutines(trainingLog.getId());
        List<RoutineDto> routines = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i) {
            routines.add(new RoutineDto(list.get(i)));
        }

        return new TrainingLogResponseDto(new TrainingLogDto(trainingLog), routines);
    }

    @Override
    public List<TrainingLogListDto> getMemberTrainingLogs(Integer userId, String clubName, String memberLoginId) {
        User user = userMapper.getUser(userId);
        user.checkValidation();

        User member = userMapper.getUserByLoginId(memberLoginId);
        member.checkValidation();

        Club club = clubMapper.getClubByName(clubName);
        club.checkValidation();

        check.isMember(user.getId(), member.getId(), club.getId());

        List<TrainingLog> list = trainingLogMapper.getTrainingLogs(member.getId());
        List<TrainingLogListDto> trainingLogs = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i) {
            trainingLogs.add(new TrainingLogListDto(list.get(i).getDate(), member.getNickname()));
        }

        return trainingLogs;
    }

    @Override
    public List<TrainingLogListDto> getMyTrainingLogs(Integer userId) {
        User user = userMapper.getUser(userId);
        user.checkValidation();

        List<TrainingLog> list = trainingLogMapper.getTrainingLogs(user.getId());
        List<TrainingLogListDto> trainingLogs = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i) {
            trainingLogs.add(new TrainingLogListDto(list.get(i).getDate(), user.getNickname()));
        }

        return trainingLogs;
    }

    @Override
    public TrainingLogResponseDto getMyTrainingLog(Integer userId, String date) {
        User user = userMapper.getUser(userId);
        user.checkValidation();

        TrainingLog trainingLog = trainingLogMapper.getTrainingLog(new GetTrainingLogParameterDto(user.getId(), date));
        trainingLog.checkValidation();

        List<Routine> list = routineMapper.getRoutines(trainingLog.getId());
        List<RoutineDto> routines = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i) {
            routines.add(new RoutineDto(list.get(i)));
        }
        return new TrainingLogResponseDto(new TrainingLogDto(trainingLog), routines);
    }

    @Override
    public void deleteTrainingLog(Integer userId, String date) {
        User user = userMapper.getUser(userId);
        user.checkValidation();

        TrainingLog trainingLog = trainingLogMapper.getTrainingLog(new GetTrainingLogParameterDto(user.getId(), date));
        trainingLog.checkValidation();

        trainingLogMapper.deleteTrainingLog(trainingLog.getId());

        routineMapper.deleteRoutines(trainingLog.getId());
    }
}
