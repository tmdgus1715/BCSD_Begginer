package org.example.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Board extends Entity {

    private Integer category;

    private Integer training_Log;
    private Integer user;
    private int like;
    private String title;
    private String content;
    private String time;
    private byte validate;

    @Builder
    private Board(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setBoardInit(Integer category, Integer user, Integer trainingLog) {
        this.training_Log = trainingLog;
        this.user = user;
        this.category = category;
        this.validate = 1;
        this.like = 0;
        LocalDateTime now = LocalDateTime.now();
        this.time = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    @Override
    public void checkValidation() {
        if (this == null || validate == 0) throw new NullPointerException("해당 게시물은 존재하지 않습니다.");
    }
}
