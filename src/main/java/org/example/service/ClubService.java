package org.example.service;

import org.example.dto.ClubDto;

public interface ClubService {
    void createClub(Integer userId, ClubDto clubDto);

    void updateName(Integer userId, String clubName, String values);

    void updateIntro(Integer userId, String clubName, String values);

    void updateMaxUser(Integer userId, String clubName, String values);

    void updatePublicity(Integer userId, String clubName, String values);

    ClubDto getMyClubInfo(Integer userId, String clubName);

    String findJoinableClub(String clubName);

    void registerClub(Integer userId, String clubName);

    void withrawClub(Integer userId, String clubName);
}
