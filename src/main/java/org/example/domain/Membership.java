package org.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Membership extends Entity {

    private Integer club;
    private Integer member;
    private byte role;
    private String join_Time;

    public Membership(Integer member, Integer club) {
        this.member = member;
        this.club = club;
    }

    public void setMembershipInit(boolean isPresident) {
        this.role = (byte) (isPresident ? 1 : 0);
        LocalDateTime now = LocalDateTime.now();
        this.join_Time = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    @Override
    public void checkValidation() {
        if (this == null) throw new NullPointerException("해당 멤버쉽은 존재하지 않습니다.");
    }
}
