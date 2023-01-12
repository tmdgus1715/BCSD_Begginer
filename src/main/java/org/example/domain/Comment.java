package org.example.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Comment extends Entity {
    private Integer board;
    private Integer user;
    private String content;
    private String time;
    private byte validate;

    @Builder
    private Comment(Integer board, Integer user, String content) {
        this.board = board;
        this.user = user;
        this.content = content;
    }

    public void SetCommentInit() {
        LocalDateTime now = LocalDateTime.now();
        this.time = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        this.validate = 1;
    }

    @Override
    public void checkValidation() {
        if (this == null || validate == 0) throw new NullPointerException("해당 댓글은 존재하지 않습니다.");

    }
}
