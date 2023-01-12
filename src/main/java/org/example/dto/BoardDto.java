package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.Board;

@Data
@NoArgsConstructor
public class BoardDto {
    private Integer id;
    private Integer category;
    private String trainingLog;
    private Integer user;
    private int like;
    private String title;
    private String content;
    private String time;

    public BoardDto(Board board, String date) {
        this.id = board.getId();
        this.trainingLog = date;
        this.category = board.getCategory();
        this.user = board.getUser();
        this.like = board.getLike();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.time = board.getTime();
    }

    public Board toEntity() {
        return Board.builder().title(title).content(content).build();
    }
}
