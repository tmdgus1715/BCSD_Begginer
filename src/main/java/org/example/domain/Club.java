package org.example.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Club extends Entity {

    private String name;
    private String intro;
    private String foundation_Time;
    private short max_User;
    private short current_User;
    private byte publicity;
    private byte validate;

    @Builder
    private Club(String name, String intro, short max_User, byte publicity) {
        this.name = name;
        this.intro = intro;
        this.max_User = max_User;
        this.publicity = publicity;
    }

    public void setClubInit() {
        this.current_User = 0;
        this.validate = 1;
        LocalDateTime now = LocalDateTime.now();
        this.foundation_Time = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    @Override
    public void checkValidation() {
        if (this == null || validate == 0) throw new NullPointerException("해당 클럽은 존재하지 않습니다.");

    }
}
