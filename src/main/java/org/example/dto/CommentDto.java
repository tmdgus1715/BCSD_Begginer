package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.Comment;

@Data
@NoArgsConstructor
public class CommentDto {
    private Integer id;
    private Integer board;
    private String nickname;
    private String content;
    private String time;


    public CommentDto(Comment comment, String userNickname) {
        this.id = comment.getId();
        this.board = comment.getBoard();
        this.nickname = userNickname;
        this.content = comment.getContent();
        this.time = comment.getTime();
    }
}
