package org.example.service.logic;

import lombok.RequiredArgsConstructor;
import org.example.domain.*;
import org.example.dto.BoardDto;
import org.example.dto.CommentDto;
import org.example.dto.RoutineDto;
import org.example.dto.TrainingLogDto;
import org.example.dto.parameterDto.GetTrainingLogParameterDto;
import org.example.dto.parameterDto.UpdateParameterDto;
import org.example.dto.responseDto.BoardListDto;
import org.example.dto.responseDto.BoardResponseDto;
import org.example.dto.responseDto.TrainingLogResponseDto;
import org.example.repository.*;
import org.example.service.BoardService;
import org.example.service.CheckValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceLogic implements BoardService {
    private final UserMapper userMapper;
    private final CategoryMapper categoryMapper;
    private final BoardMapper boardMapper;
    private final CommentMapper commentMapper;
    private final TrainingLogMapper trainingLogMapper;
    private final RoutineMapper routineMapper;
    private final CheckValidationService check;

    @Override
    public void writePost(String categoryName, Integer userId, BoardDto newPost) {
        check.checkValidationBoard(newPost);

        User user = userMapper.getUser(userId);
        user.checkValidation();

        Category category = categoryMapper.getCategoryByName(categoryName);
        category.checkValidation();

        Board newBoard = newPost.toEntity();
        TrainingLog trainingLog = trainingLogMapper.getTrainingLog(new GetTrainingLogParameterDto(user.getId(), newPost.getTrainingLog()));
        newBoard.setBoardInit(category.getId(), user.getId(), trainingLog.getId());

        boardMapper.createPost(newBoard);
    }

    @Override
    public void updateTitle(Integer userId, Integer postId, String newTitle) {
        check.checkValidationBoardTitle(newTitle);

        User user = userMapper.getUser(userId);
        user.checkValidation();

        Board board = boardMapper.getBoard(postId);
        board.checkValidation();

        check.checkAuthority(board.getUser(), user.getId());

        UpdateParameterDto updateParameterDto = UpdateParameterDto.builder().id(postId).value(newTitle).build();

        boardMapper.updateTitle(updateParameterDto);
    }

    @Override
    public void updateContent(Integer userId, Integer postId, String newContent) {
        check.checkValidationBoardContent(newContent);

        User user = userMapper.getUser(userId);
        user.checkValidation();

        Board board = boardMapper.getBoard(postId);
        board.checkValidation();

        check.checkAuthority(board.getUser(), user.getId());

        UpdateParameterDto updateParameterDto = UpdateParameterDto.builder().id(postId).value(newContent).build();

        boardMapper.updateContent(updateParameterDto);
    }

    @Override
    public void updateTrainingLog(Integer userId, Integer postId, String newTrainingLogDate) {
        check.checkValidationBoardTrainingLogDate(newTrainingLogDate);

        User user = userMapper.getUser(userId);
        user.checkValidation();

        Board board = boardMapper.getBoard(postId);
        board.checkValidation();

        check.checkAuthority(board.getUser(), user.getId());

        TrainingLog newTrainingLog = trainingLogMapper.getTrainingLog(new GetTrainingLogParameterDto(user.getId(), newTrainingLogDate));
        newTrainingLog.checkValidation();

        UpdateParameterDto updateParameterDto = UpdateParameterDto.builder().id(postId).integerValue(newTrainingLog.getId()).build();

        boardMapper.updateTrainingLog(updateParameterDto);
    }

    @Override
    public void deletePost(Integer userId, Integer postId) {
        User user = userMapper.getUser(userId);
        user.checkValidation();

        Board board = boardMapper.getBoard(postId);
        board.checkValidation();

        check.checkAuthority(board.getUser(), user.getId());

        boardMapper.deletePost(postId);
    }

    @Override
    public void recommendPost(Integer postId) {
        Board board = boardMapper.getBoard(postId);
        board.checkValidation();

        boardMapper.increaseLike(postId);
    }

    @Override
    public BoardResponseDto getPost(Integer postId) {
        Board board = boardMapper.getBoard(postId);
        board.checkValidation();

        TrainingLog trainingLog = trainingLogMapper.getTrainingLogById(board.getTraining_Log());
        trainingLog.checkValidation();

        List<Routine> list = routineMapper.getRoutines(trainingLog.getId());
        List<RoutineDto> routines = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i) {
            routines.add(new RoutineDto(list.get(i)));
        }

        TrainingLogResponseDto trainingLogResponseDto = new TrainingLogResponseDto(new TrainingLogDto(trainingLog), routines);
        BoardDto post = new BoardDto(board, trainingLog.getDate());

        return new BoardResponseDto(trainingLogResponseDto, post);
    }

    @Override//본인의 게시물 목록 확인
    public List<BoardListDto> getUserPosts(Integer userId) {
        User user = userMapper.getUser(userId);
        user.checkValidation();

        List<Board> list = boardMapper.getUserPosts(user.getId());
        List<BoardListDto> userPosts = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i) {
            Board element = list.get(i);
            userPosts.add(BoardListDto.builder()
                    .id(element.getId())
                    .title(element.getTitle())
                    .time(element.getTime())
                    .user(user.getNickname())
                    .like(element.getLike())
                    .build());
        }

        return userPosts;
    }

    @Override
    public List<BoardListDto> getPostsOrderByLike(String categoryName) {
        Category category = categoryMapper.getCategoryByName(categoryName);
        category.checkValidation();

        List<Board> list = boardMapper.getPostsOrderByLike(category.getId());
        List<BoardListDto> posts = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i) {
            Board element = list.get(i);
            User user = userMapper.getUser(element.getUser());
            posts.add(BoardListDto.builder()
                    .id(element.getId())
                    .title(element.getTitle())
                    .time(element.getTime())
                    .user(user.getNickname())
                    .like(element.getLike())
                    .build());
        }

        return posts;
    }

    @Override
    public List<BoardListDto> getPostsOrderByDate(String categoryName, String way) {
        Category category = categoryMapper.getCategoryByName(categoryName);
        category.checkValidation();

        List<Board> list = new ArrayList<>();

        if (way.equals("asc")) {
            list = boardMapper.getPostsOrderByDateAsc(category.getId());
        } else if (way.equals("desc")) {
            list = boardMapper.getPostsOrderByDateDesc(category.getId());
        } else {
            throw new RuntimeException("way is not found");
        }

        List<BoardListDto> posts = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i) {
            Board element = list.get(i);
            User user = userMapper.getUser(element.getUser());
            posts.add(BoardListDto.builder()
                    .id(element.getId())
                    .title(element.getTitle())
                    .time(element.getTime())
                    .user(user.getNickname())
                    .like(element.getLike())
                    .build());
        }

        return posts;
    }

    @Override
    public void leaveComment(Integer postId, Integer userId, String comment) {
        check.checkValidationComment(comment);

        User user = userMapper.getUser(userId);
        user.checkValidation();

        Board board = boardMapper.getBoard(postId);
        board.checkValidation();

        Comment newComment = Comment.builder().board(postId).user(user.getId()).content(comment).build();
        newComment.SetCommentInit();

        commentMapper.createComment(newComment);
    }

    @Override
    public List<CommentDto> getComments(Integer postId) {
        Board board = boardMapper.getBoard(postId);
        board.checkValidation();

        List<Comment> list = commentMapper.getComments(postId);
        List<CommentDto> comments = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i) {
            Comment element = list.get(i);
            User user = userMapper.getUser(element.getUser());
            user.checkValidation();
            comments.add(new CommentDto(list.get(i), user.getNickname()));
        }

        return comments;
    }

    @Override
    public void deleteComment(Integer postId, Integer userId, Integer commentId) {
        User user = userMapper.getUser(userId);
        user.checkValidation();

        Board board = boardMapper.getBoard(postId);
        board.checkValidation();

        Comment comment = commentMapper.getComment(commentId);
        comment.checkValidation();

        check.checkAuthority(comment.getUser(), user.getId());

        commentMapper.deleteComment(commentId);
    }

}
