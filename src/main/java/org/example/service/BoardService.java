package org.example.service;

import org.example.dto.BoardDto;
import org.example.dto.CommentDto;
import org.example.dto.responseDto.BoardListDto;
import org.example.dto.responseDto.BoardResponseDto;

import java.util.List;

public interface BoardService {
    void writePost(String categoryName, Integer userId, BoardDto newPost);

    void updateTitle(Integer userId, Integer postId, String value);

    void updateContent(Integer userId, Integer postId, String value);

    void updateTrainingLog(Integer userId, Integer postId, String value);

    void deletePost(Integer userId, Integer postId);

    void recommendPost(Integer postId);

    BoardResponseDto getPost(Integer postId);

    List<BoardListDto> getUserPosts(Integer userId);

    List<BoardListDto> getPostsOrderByLike(String categoryName);

    List<BoardListDto> getPostsOrderByDate(String categoryName, String way);

    void leaveComment(Integer postId, Integer userId, String comment);

    void deleteComment(Integer postId, Integer userId, Integer commentId);

    List<CommentDto> getComments(Integer postId);
}
