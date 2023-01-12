package org.example.service.logic;

import lombok.RequiredArgsConstructor;
import org.example.domain.User;
import org.example.dto.UserDto;
import org.example.dto.parameterDto.UpdateParameterDto;
import org.example.dto.requestDto.LoginUserRequestDto;
import org.example.repository.ClubMapper;
import org.example.repository.MembershipMapper;
import org.example.repository.UserMapper;
import org.example.service.CheckValidationService;
import org.example.service.JwtService;
import org.example.service.UserService;
import org.example.util.security.Encryption;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceLogic implements UserService {

    private final UserMapper userMapper;
    private final ClubMapper clubMapper;
    private final MembershipMapper membershipMapper;
    private final JwtService jwtService;
    private final Encryption encryption;
    private final CheckValidationService check;

    @Override
    public void create(UserDto newUserDto) {
        check.checkValidationUser(newUserDto);

        String hashedPassword = encryption.encrypt(newUserDto.getPassword());
        newUserDto.setPassword(hashedPassword);

        User newUser = newUserDto.toEntity();
        newUser.setUserInit();

        userMapper.create(newUser);
    }

    @Override
    public String login(LoginUserRequestDto loginUserRequestDto) {
        User user = userMapper.getUserByLoginId(loginUserRequestDto.getLoginId());
        user.checkValidation();

        encryption.checkPassword(loginUserRequestDto.getPassword(), user.getPassword());

        String token = jwtService.createToken(user.getId());

        return token;
    }

    @Override
    public UserDto getUser(Integer userId) {
        User user = userMapper.getUser(userId);
        user.checkValidation();

        UserDto userDto = new UserDto(user);

        return userDto;
    }

    @Override
    public UserDto getUserByLoginId(String loginId) {
        User user = userMapper.getUserByLoginId(loginId);

        return new UserDto(user);
    }

    @Override
    public void updatePassword(Integer userId, String newPassword) {
        check.checkValidationUserPassword(newPassword);

        User user = userMapper.getUser(userId);
        user.checkValidation();

        String hashedPassword = encryption.encrypt(newPassword);

        UpdateParameterDto updateParameterDto = UpdateParameterDto.builder().id(user.getId()).value(hashedPassword).build();

        userMapper.updatePassword(updateParameterDto);
    }

    @Override
    public void updateNickname(Integer userId, String newNickname) {
        check.checkValidationUserNickname(newNickname);

        User user = userMapper.getUser(userId);
        user.checkValidation();

        UpdateParameterDto updateParameterDto = UpdateParameterDto.builder().id(user.getId()).value(newNickname).build();

        userMapper.updateNickname(updateParameterDto);
    }

    @Override
    public void delete(Integer userId, String password) {
        User user = userMapper.getUser(userId);
        user.checkValidation();

        encryption.checkPassword(password, user.getPassword());

        userMapper.delete(user.getId());

        clubMapper.withdrawAllClub(user.getId());

        membershipMapper.deleteUserAllMembership(user.getId());
    }


}
