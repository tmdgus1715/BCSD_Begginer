package org.example.repository;

import org.example.domain.Board;
import org.example.dto.parameterDto.UpdateParameterDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardMapper {
    List<Board> getUserPosts(Integer userId);

    /**
     * board의 id로 Board객체 반환, getPost와는 다름
     */
    Board getBoard(Integer postId);

    void createPost(Board newBoard);

    void updateTitle(UpdateParameterDto updateParameterDto);

    void updateContent(UpdateParameterDto updateParameterDto);

    void updateTrainingLog(UpdateParameterDto updateParameterDto);

    void deletePost(Integer postId);

    void increaseLike(Integer postId);

    List<Board> getPostsOrderByLike(Integer categoryId);

    List<Board> getPostsOrderByDateAsc(Integer categoryId);

    List<Board> getPostsOrderByDateDesc(Integer categoryId);
}
