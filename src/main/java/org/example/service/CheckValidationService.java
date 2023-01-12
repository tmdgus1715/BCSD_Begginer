package org.example.service;

import org.example.dto.*;

public interface CheckValidationService {
    void checkAuthority(Integer work, Integer author);

    void checkDuplicatedCategoryId(Integer id);

    void checkDuplicatedCategoryName(String newName);

    void checkValidationCategory(CategoryDto newCategoryDto);

    void checkValidationClubName(String name);

    void checkValidationClubIntro(String intro);

    void checkValidationClubMaxUser(int maxUser);

    void checkValidationClubPublicity(byte publicity);

    void checkDuplicatedClubName(String name);

    void checkValidationCategoryName(String newName);

    void checkValidationCategoryId(Integer id);

    void checkValidationClub(ClubDto newClubDto);

    void checkValidationBoard(BoardDto newPost);

    void checkValidationBoardTitle(String newTitle);

    void checkValidationBoardContent(String newContent);

    void checkValidationBoardTrainingLogDate(String newTrainingLogDate);

    void checkValidationComment(String comment);

    void checkValidationExerciseCode(ExerciseCodeDto exerciseCodeDto);

    void checkDuplicatedExerciseCodeName(String name);

    void checkValidationExerciseCodeName(String newName);

    void checkValidationTrainingLog(TrainingLogDto trainingLogDto, Integer userId);

    void checkValidationTrainingLogDate(String date, Integer userId);

    void checkValidationTrainingLogWeight(byte weight);

    void checkValidationTrainingLogBodypart(String bodypart);

    void checkValidationTrainingLogTrainingTime(short trainingTime);

    void checkDuplicatedTrainingLogDate(String date, Integer userId);

    void checkValidationUser(UserDto userDto);

    void checkValidationUserLoginId(String loginId);

    void checkValidationUserPassword(String password);

    void checkValidationUserNickname(String nickname);

    void checkValidationUserGender(byte gender);

    void checkDuplicatedUserLoginId(String loginId);

    void checkDuplicatedUserNickname(String nickname);

    void isPrivateClub(byte publicity);

    void isFullClub(short max_user, short current_user);

    void checkValidationMembership(Integer userId, Integer clubId);

    void checkDuplicatedMembership(Integer userId, Integer clubId);

    void isPresidentMember(Integer id, Integer id1);

    void checkValidationRoutine(RoutineDto routineDto);

    void checkValidationRoutineExercise(Integer exercise);

    void checkValidationRoutineWeight(int weight);

    void checkValidationRoutineReps(short reps);

    void checkValidationRoutineSet(short set);

    void checkValidationRoutineRecess(int recess);

    void checkValidationRoutineRpe(byte rpe);

    void isMember(Integer userId, Integer memberId, Integer clubId);
}
