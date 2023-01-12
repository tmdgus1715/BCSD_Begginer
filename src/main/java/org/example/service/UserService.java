package org.example.service;

import org.example.dto.UserDto;
import org.example.dto.requestDto.LoginUserRequestDto;

public interface UserService {
    void create(UserDto user);

    UserDto getUser(Integer id);

    UserDto getUserByLoginId(String loginId);

    void updatePassword(Integer userId, String newPassword);

    void updateNickname(Integer userId, String newNickname);

    void delete(Integer userId, String password);

    String login(LoginUserRequestDto loginUserRequestDto);
}
