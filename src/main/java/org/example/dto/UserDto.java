package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.User;

@Data
@NoArgsConstructor
public class UserDto {
    private String loginId;
    private String password;
    private String nickname;
    private byte gender;

    public UserDto(User user) {
        this.loginId = user.getLogin_Id();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.gender = user.getGender();
    }

    public User toEntity() {
        User user = User.builder().password(this.password).login_Id(this.loginId).nickname(this.nickname).gender(this.gender).build();
        return user;
    }
}

