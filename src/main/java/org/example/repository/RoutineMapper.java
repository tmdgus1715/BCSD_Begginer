package org.example.repository;

import org.example.domain.Routine;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutineMapper {
    void createRoutine(Routine newRoutine);

    List<Routine> getRoutines(Integer trainingLogId);

    void deleteRoutines(Integer trainingLogId);
}
