package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ExerciseCodeDto;
import org.example.dto.requestDto.UpdateRequestDto;
import org.example.service.ExerciseCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/exerciseCodes", produces = "application/json; charset=utf8")
public class ExerciseCodeController {
    private final ExerciseCodeService exerciseCodeService;

    @PostMapping
    public ResponseEntity<Object> createExerciseCode(@RequestBody ExerciseCodeDto newExerciseCode) throws URISyntaxException {
        exerciseCodeService.createExerciseCode(newExerciseCode);
        return ResponseEntity.created(new URI("http://localhost:8080/exerciseCodes/list")).build();
    }

    @PatchMapping("/{ExerciseCode_Name}")
    public ResponseEntity<Object> updateExerciseCode(@PathVariable String ExerciseCode_Name, @RequestBody UpdateRequestDto updateRequestDto) {
        switch (updateRequestDto.getKey()) {
            case "exercise":
                exerciseCodeService.updateExerciseCodeName(ExerciseCode_Name, updateRequestDto.getValue());
                return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<ExerciseCodeDto>> getExerciseCodes() {
        List<ExerciseCodeDto> exerciseCodeDtos = exerciseCodeService.getExerciseCodes();
        return ResponseEntity.ok(exerciseCodeDtos);
    }

    @DeleteMapping("/{ExerciseCode_Name}")
    public ResponseEntity<Object> deleteExerciseCode(@PathVariable String ExerciseCode_Name) {
        exerciseCodeService.deleteExerciseCode(ExerciseCode_Name);
        return ResponseEntity.ok().build();
    }
}
