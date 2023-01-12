package org.example.repository;

import org.example.domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    void createComment(Comment newComment);

    List<Comment> getComments(Integer postId);

    Comment getComment(Integer commentId);

    void deleteComment(Integer commentId);
}
