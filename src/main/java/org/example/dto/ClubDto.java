package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.Club;

@Data
@NoArgsConstructor
public class ClubDto {
    private String name;
    private String intro;
    private String foundationTime;
    private short maxUser;
    private short currentUser;
    private byte publicity;

    public ClubDto(Club club) {
        this.name = club.getName();
        this.intro = club.getIntro();
        this.foundationTime = club.getFoundation_Time();
        this.maxUser = club.getMax_User();
        this.currentUser = club.getCurrent_User();
        this.publicity = club.getPublicity();
    }

    public Club toEntity() {
        return Club.builder().name(name).intro(intro).max_User(maxUser).publicity(publicity).build();
    }
}
