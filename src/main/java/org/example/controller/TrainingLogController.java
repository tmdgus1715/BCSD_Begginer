package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.requestDto.TrainingLogRequestDto;
import org.example.dto.requestDto.TrainingLogUpdateRequestDto;
import org.example.dto.responseDto.TrainingLogListDto;
import org.example.dto.responseDto.TrainingLogResponseDto;
import org.example.service.JwtService;
import org.example.service.TrainingLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/trainingLogs", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
public class TrainingLogController {
    private final TrainingLogService trainingLogService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<Object> recordTrainingLog(@RequestHeader("jwtToken") String jwtToken, @RequestBody TrainingLogRequestDto newTrainingLog) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        trainingLogService.recordTrainingLog(userId, newTrainingLog);
        return ResponseEntity.created(new URI("http://localhost:8080/trainingLogs/list")).build();
    }

    @PatchMapping("/{date}")
    public ResponseEntity<Object> updateTrainingLog(@RequestHeader("jwtToken") String jwtToken, @PathVariable String date,
                                                    @RequestBody TrainingLogUpdateRequestDto trainingLogUpdateRequestDto) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        switch (trainingLogUpdateRequestDto.getKey()) {
            case "weight":
                trainingLogService.updateWeight(userId, date, trainingLogUpdateRequestDto.getValue());
                return ResponseEntity.ok().build();
            case "bodypart":
                trainingLogService.updateBodypart(userId, date, trainingLogUpdateRequestDto.getValue());
                return ResponseEntity.ok().build();
            case "trainingTime":
                trainingLogService.updateTrainingTime(userId, date, trainingLogUpdateRequestDto.getValue());
                return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{date}")
    public ResponseEntity<Object> deleteTrainingLog(@RequestHeader("jwtToken") String jwtToken, @PathVariable String date) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        trainingLogService.deleteTrainingLog(userId, date);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{date}")//본인trainingLog를 조회
    public ResponseEntity<TrainingLogResponseDto> getMyTrainingLog(@RequestHeader("jwtToken") String jwtToken, @PathVariable String date) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        TrainingLogResponseDto trainingLogResponseDto = trainingLogService.getMyTrainingLog(userId, date);
        return ResponseEntity.ok(trainingLogResponseDto);
    }

    @GetMapping("/{clubName}/{memberLoginId}/{date}")//멤버의 trainingLog를 조회
    public ResponseEntity<TrainingLogResponseDto> getMemberTrainingLog(@RequestHeader("jwtToken") String jwtToken, @PathVariable String clubName
            , @PathVariable String memberLoginId, @PathVariable String date) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        TrainingLogResponseDto trainingLogResponseDto = trainingLogService.getMemberTrainingLog(userId, clubName, memberLoginId, date);
        return ResponseEntity.ok(trainingLogResponseDto);
    }

    @GetMapping("/list")
    public ResponseEntity<List<TrainingLogListDto>> getMyTrainingLogs(@RequestHeader("jwtToken") String jwtToken) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        List<TrainingLogListDto> trainingLogListDto = trainingLogService.getMyTrainingLogs(userId);
        return ResponseEntity.ok(trainingLogListDto);
    }

    @GetMapping("/{clubName}/{memberLoginId}/list")
    public ResponseEntity<List<TrainingLogListDto>> getMemberTrainingLogs(@RequestHeader("jwtToken") String jwtToken, @PathVariable String clubName
            , @PathVariable String memberLoginId) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        List<TrainingLogListDto> trainingLogListDto = trainingLogService.getMemberTrainingLogs(userId, clubName, memberLoginId);
        return ResponseEntity.ok(trainingLogListDto);
    }
}
