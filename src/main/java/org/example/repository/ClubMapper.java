package org.example.repository;

import org.example.domain.Club;
import org.example.dto.parameterDto.UpdateParameterDto;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubMapper {
    Club getClubByName(String name);

    void createClub(Club newClub);

    void deleteClub(Integer clubId);

    void updateName(UpdateParameterDto updateParameterDto);

    void updateIntro(UpdateParameterDto updateParameterDto);

    void updateMaxUser(UpdateParameterDto updateParameterDto);

    void updatePublicity(UpdateParameterDto updateParameterDto);

    void increaseCurrentUser(Integer clubId);

    void decreaseCurrentUser(Integer clubId);

    void withdrawAllClub(Integer userId);
}
