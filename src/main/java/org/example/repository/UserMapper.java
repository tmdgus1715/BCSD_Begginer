package org.example.repository;

import org.example.domain.User;
import org.example.dto.parameterDto.UpdateParameterDto;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    public User getUser(Integer id);

    public User getUserByLoginId(String loginId);

    public void create(User user);

    public void updatePassword(UpdateParameterDto updateParameterDto);

    public void updateNickname(UpdateParameterDto updateParameterDto);

    public void delete(Integer id);


    User getUserByNickname(String nickname);
}
