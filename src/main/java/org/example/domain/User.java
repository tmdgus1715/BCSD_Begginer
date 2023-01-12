package org.example.domain;

import lombok.*;


@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class User extends Entity {
    private int level;
    private String password;

    private String login_Id;
    private String nickname;
    private byte gender;
    private byte validate;

    @Builder
    private User(String login_Id, String password, String nickname, byte gender) {
        this.login_Id = login_Id;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
    }

    public void setUserInit() {
        this.level = 0;
        this.validate = 1;
    }

    @Override
    public void checkValidation() {
        if (this == null || validate == 0) throw new NullPointerException("해당 유저는 존재하지 않습니다.");
    }
}

