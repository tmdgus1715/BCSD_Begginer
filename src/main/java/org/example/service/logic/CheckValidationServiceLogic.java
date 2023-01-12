package org.example.service.logic;

import lombok.RequiredArgsConstructor;
import org.example.domain.*;
import org.example.dto.*;
import org.example.dto.parameterDto.GetTrainingLogParameterDto;
import org.example.dto.parameterDto.IsMemberParameterDto;
import org.example.dto.parameterDto.MembershipParameterDto;
import org.example.repository.*;
import org.example.service.CheckValidationService;
import org.example.util.exception.BadRequestException;
import org.example.util.exception.DuplicatedException;
import org.example.util.exception.ForbiddenException;
import org.example.util.exception.InvalidRangeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckValidationServiceLogic implements CheckValidationService {
    private final CategoryMapper categoryMapper;
    private final ClubMapper clubMapper;
    private final MembershipMapper membershipMapper;
    private final ExerciseCodeMapper exerciseMapper;
    private final UserMapper userMapper;
    private final TrainingLogMapper trainingLogMapper;

    @Override
    public void checkAuthority(Integer work, Integer author) {
        if (!work.equals(author)) throw new ForbiddenException("작성자만 변경/삭제 가능합니다.");
    }
    //------------------------------Board-------------------------------

    @Override
    public void checkValidationBoard(BoardDto newPost) {
        checkValidationBoardContent(newPost.getContent());
        checkValidationBoardTitle(newPost.getTitle());
        checkValidationBoardTrainingLogDate(newPost.getTrainingLog());
    }

    @Override
    public void checkValidationBoardTitle(String newTitle) {
        if (newTitle.length() < 1) throw new InvalidRangeException("제목은 비워둘 수 없습니다.");
        else if (newTitle.length() > 45) throw new InvalidRangeException("제목의 글자수는 45자 제한입니다.");
    }

    @Override
    public void checkValidationBoardContent(String newContent) {//416
        if (newContent.length() < 1) throw new InvalidRangeException("본문은 비워둘 수 없습니다.");
        else if (newContent.length() > 1000) throw new InvalidRangeException("본문의 글자수는 1000자 제한입니다.");
    }

    @Override//400
    public void checkValidationBoardTrainingLogDate(String newTrainingLogDate) {
        String pattern = "\\d{4}-\\d{2}-\\d{2}";
        if (!Pattern.matches(pattern, newTrainingLogDate) || newTrainingLogDate.length() != 10)
            throw new InvalidRangeException("지정된 날짜 형식은 0000-00-00 형식입니다");
    }
    //------------------------------Comment--------------------------

    @Override
    public void checkValidationComment(String comment) {
        if (comment.length() < 1) throw new InvalidRangeException("댓글은 비워둘 수 없습니다.");
        else if (comment.length() > 500) throw new InvalidRangeException("댓글의 글자수는 500자 제한입니다.");
    }

    //------------------------------Category-------------------------
    @Override
    public void checkValidationCategory(CategoryDto newCategoryDto) {
        checkValidationCategoryId(newCategoryDto.getId());
        checkValidationCategoryName(newCategoryDto.getName());
    }

    @Override
    public void checkValidationCategoryName(String newName) {
        if (newName.length() < 1) throw new InvalidRangeException("카테고리 이름은 비워둘 수 없습니다.");
        else if (newName.length() > 15) throw new InvalidRangeException("카테고리 이름의 글자수는 15자 제한입니다.");
        checkDuplicatedCategoryName(newName);
    }

    @Override
    public void checkValidationCategoryId(Integer id) {
        if (id < 0 || id > 99) throw new InvalidRangeException("카테고리 id의 유효범위는 0~99입니다");
        checkDuplicatedCategoryId(id);
    }

    @Override
    public void checkDuplicatedCategoryId(Integer id) {
        Category existCategory = categoryMapper.getCategory(id);
        if (existCategory != null) throw new DuplicatedException("이미 사용중인 카테고리id입니다.");
    }

    @Override
    public void checkDuplicatedCategoryName(String name) {
        Category existCategory = categoryMapper.getCategoryByName(name);
        if (existCategory != null) throw new DuplicatedException("이미 사용중인 카테고리이름입니다.");
    }

    //------------------------------Club-------------------------
    @Override
    public void checkValidationClub(ClubDto newClubDto) {
        checkValidationClubName(newClubDto.getName());
        checkValidationClubIntro(newClubDto.getIntro());
        checkValidationClubMaxUser(newClubDto.getMaxUser());
        checkValidationClubPublicity(newClubDto.getPublicity());
    }

    @Override
    public void checkValidationClubName(String name) {
        if (name.length() < 1) throw new InvalidRangeException("클럽이름은 비워둘 수 없습니다.");
        else if (name.length() > 20) throw new InvalidRangeException("클럽이름은 최대 20자입니다.");
        checkDuplicatedClubName(name);
    }

    @Override
    public void checkValidationClubIntro(String intro) {
        if (intro.length() < 1) throw new InvalidRangeException("소개글은 비워둘 수 없습니다.");
        else if (intro.length() > 45) throw new InvalidRangeException("소개글은 최대 45자입니다.");
    }

    @Override
    public void checkValidationClubMaxUser(int maxUser) {

        if (maxUser < 2 || maxUser > 65535) throw new InvalidRangeException("최대 유저의 유효범위는 2~65535명입니다");
    }


    @Override
    public void checkValidationClubPublicity(byte publicity) {
        if (publicity != 0 && publicity != 1) throw new InvalidRangeException("공개는 1, 비공개는 0입니다");
    }


    @Override//400
    public void checkDuplicatedClubName(String name) {
        Club existClub = null;
        existClub = clubMapper.getClubByName(name);
        if (existClub != null) throw new DuplicatedException("이미 사용중인 club이름입니다.");
    }

    @Override
    public void isPrivateClub(byte publicity) {
        if (publicity == 0) throw new BadRequestException("비공개 클럽은 가입/조회가 불가능합니다.");
    }

    @Override
    public void isFullClub(short maxUser, short currentUser) {
        if (maxUser <= currentUser) throw new BadRequestException("클럽이 가득 찼습니다");
    }


    //--------------------------ExerciseCode------------------------------------
    @Override
    public void checkValidationExerciseCode(ExerciseCodeDto exerciseCodeDto) {
        checkValidationExerciseCodeName(exerciseCodeDto.getExercise());
    }

    @Override
    public void checkValidationExerciseCodeName(String exercise) {
        if (exercise.length() < 1) throw new InvalidRangeException("종목이름은 비워둘 수 없습니다.");
        else if (exercise.length() > 45) throw new InvalidRangeException("종목이름은 최대 45자입니다.");
        checkDuplicatedExerciseCodeName(exercise);
    }

    @Override
    public void checkDuplicatedExerciseCodeName(String exercise) {
        ExerciseCode existExerciseCode = exerciseMapper.getExerciseCodeByName(exercise);
        if (existExerciseCode != null) throw new DuplicatedException("이미 사용중인 종목이름입니다.");

    }


    //--------------------------Membership---------------------------------------

    @Override
    public void checkValidationMembership(Integer userId, Integer clubId) {
        checkDuplicatedMembership(userId, clubId);
    }

    @Override//400
    public void checkDuplicatedMembership(Integer userId, Integer clubId) {
        Membership existMembership = membershipMapper.getMembership(new MembershipParameterDto(userId, clubId));
        if (existMembership != null) throw new BadRequestException("이미 해당클럽에 가입했습니다.");
    }

    @Override
    public void isPresidentMember(Integer userId, Integer clubId) {
        Membership presidentMembership = membershipMapper.getMembership(new MembershipParameterDto(userId, clubId));
        if (presidentMembership == null || presidentMembership.getRole() != 1)
            throw new ForbiddenException("회장만 변경 가능합니다");
    }

    @Override
    public void isMember(Integer userId, Integer memberId, Integer clubId) {
        Membership isMember = membershipMapper.isMember(new IsMemberParameterDto(userId, memberId, clubId));
        if (isMember == null) throw new ForbiddenException("멤버만 조회가능합니다.");
    }

    //--------------------------Routine------------------------------------------
    @Override
    public void checkValidationRoutine(RoutineDto routineDto) {
        checkValidationRoutineExercise(routineDto.getExercise());
        checkValidationRoutineWeight(routineDto.getWeight());
        checkValidationRoutineReps(routineDto.getReps());
        checkValidationRoutineSet(routineDto.getSet());
        checkValidationRoutineRecess(routineDto.getRecess());
        checkValidationRoutineRpe(routineDto.getRpe());
    }

    @Override
    public void checkValidationRoutineExercise(Integer exercise) {
        ExerciseCode exerciseCode = exerciseMapper.getExerciseCode(exercise);
        exerciseCode.checkValidation();
    }

    @Override
    public void checkValidationRoutineWeight(int weight) {
        if (weight < 0 || weight > 9999) throw new InvalidRangeException("중량의 유효범위는 0~9999kg입니다.");
    }

    @Override
    public void checkValidationRoutineReps(short reps) {
        if (reps < 0 || reps > 255) throw new InvalidRangeException("반복횟수의 유효범위는 0~255회입니다.");
    }

    @Override
    public void checkValidationRoutineSet(short set) {
        if (set < 0 || set > 255) throw new InvalidRangeException("세트 수의 유효범위는 0~255회입니다.");
    }

    @Override
    public void checkValidationRoutineRecess(int recess) {
        if (recess < 0 || recess > 9999) throw new InvalidRangeException("휴식시간의 유효범위는 0~9999초입니다.");
    }

    @Override
    public void checkValidationRoutineRpe(byte rpe) {
        if (rpe < 1 || rpe > 10) throw new InvalidRangeException("rpe의 유효범위는 1~10입니다.");
    }


    //--------------------------TrainingLog--------------------------------------

    @Override
    public void checkValidationTrainingLog(TrainingLogDto trainingLogDto, Integer userId) {
        checkValidationTrainingLogDate(trainingLogDto.getDate(), userId);
        checkValidationTrainingLogWeight(trainingLogDto.getWeight());
        checkValidationTrainingLogBodypart(trainingLogDto.getBodypart());
        checkValidationTrainingLogTrainingTime(trainingLogDto.getTrainingTime());
    }

    @Override
    public void checkValidationTrainingLogDate(String date, Integer userId) {
        String pattern = "\\d{4}-\\d{2}-\\d{2}";
        if (!Pattern.matches(pattern, date) || date.length() != 10)
            throw new BadRequestException("지정된 날짜 형식은 0000-00-00 형식입니다");
        checkDuplicatedTrainingLogDate(date, userId);
    }

    @Override
    public void checkValidationTrainingLogWeight(byte weight) {
        if (weight < 1 || weight > 255) throw new InvalidRangeException("체중의 유효범위는 1~255kg입니다.");
    }

    @Override
    public void checkValidationTrainingLogBodypart(String bodypart) {
        if (bodypart.length() < 1) throw new InvalidRangeException("운동부위는 비워둘 수 없습니다.");
        else if (bodypart.length() > 45) throw new InvalidRangeException("운동부위는 최대 45자입니다.");
    }

    @Override
    public void checkValidationTrainingLogTrainingTime(short trainingTime) {
        if (trainingTime < 1 || trainingTime > 9999) throw new InvalidRangeException("총 운동시간의 유효범위는 1~9999분입니다.");
    }

    @Override
    public void checkDuplicatedTrainingLogDate(String date, Integer userId) {
        TrainingLog existTrainingLog = trainingLogMapper.getTrainingLog(new GetTrainingLogParameterDto(userId, date));
        if (existTrainingLog != null) throw new DuplicatedException("해당 날짜의 운동일지가 이미 기록되어 있습니다.");
    }


    //--------------------------User---------------------------------------------

    @Override
    public void checkValidationUser(UserDto userDto) {
        checkValidationUserLoginId(userDto.getLoginId());
        checkValidationUserPassword(userDto.getPassword());
        checkValidationUserNickname(userDto.getNickname());
        checkValidationUserGender(userDto.getGender());
    }

    @Override
    public void checkValidationUserLoginId(String loginId) {
        if (loginId.contains(" ")) throw new BadRequestException("ID는 공백을 포함할 수 없습니다.");
        if (loginId.length() < 1) throw new InvalidRangeException("ID는 비워둘 수 없습니다.");
        else if (loginId.length() > 45) throw new InvalidRangeException("ID는 최대 45자입니다.");
        checkDuplicatedUserLoginId(loginId);
    }

    @Override
    public void checkValidationUserPassword(String password) {
        if (password.contains(" ")) throw new BadRequestException("비밀번호는 공백을 포함할 수 없습니다.");
        if (password.length() < 1) throw new InvalidRangeException("비밀번호는 비워둘 수 없습니다.");
        else if (password.length() > 45) throw new InvalidRangeException("비밀번호는 최대 45자입니다.");
    }

    @Override
    public void checkValidationUserNickname(String nickname) {
        if (nickname.contains(" ")) throw new BadRequestException("닉네임은 공백을 포함할 수 없습니다.");
        if (nickname.length() < 1) throw new InvalidRangeException("닉네임은 비워둘 수 없습니다.");
        else if (nickname.length() > 15) throw new InvalidRangeException("닉네임은 최대 15자입니다.");
        checkDuplicatedUserNickname(nickname);
    }

    @Override
    public void checkValidationUserGender(byte gender) {
        if (gender != 0 && gender != 1) throw new InvalidRangeException("남자는 1, 여자는 0입니다");
    }

    @Override
    public void checkDuplicatedUserLoginId(String loginId) {
        User existUser = null;
        existUser = userMapper.getUserByLoginId(loginId);
        if (existUser != null) throw new DuplicatedException("이미 사용중인 Id입니다");
    }

    @Override
    public void checkDuplicatedUserNickname(String nickname) {
        User existUser = null;
        existUser = userMapper.getUserByNickname(nickname);
        if (existUser != null) throw new DuplicatedException("이미 사용중인 닉네임입니다");
    }

}
