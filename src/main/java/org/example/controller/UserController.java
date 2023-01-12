package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserDto;
import org.example.dto.requestDto.LoginUserRequestDto;
import org.example.dto.requestDto.UpdateRequestDto;
import org.example.service.JwtService;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/users", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;


    @PostMapping("/sign_up")
    public ResponseEntity<Object> createUser(@RequestBody UserDto newUser) throws URISyntaxException {
        userService.create(newUser);
        return ResponseEntity.created(new URI("http://localhost:8080/users/sign_in")).build();
    }

    @PostMapping("/sign_in")
    public ResponseEntity<String> login(@RequestBody LoginUserRequestDto loginUserRequestDto) {
        String jwtToken = userService.login(loginUserRequestDto);
        return ResponseEntity.ok(jwtToken);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteUser(@RequestHeader("jwtToken") String jwtToken, @RequestBody String password) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        userService.delete(userId, password);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Object> updateUser(@RequestHeader("jwtToken") String jwtToken, @RequestBody UpdateRequestDto updateRequestDto) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        switch (updateRequestDto.getKey()) {
            case "password":
                userService.updatePassword(userId, updateRequestDto.getValue());
                return ResponseEntity.ok().build();
            case "nickname":
                userService.updateNickname(userId, updateRequestDto.getValue());
                return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<UserDto> getUserInfo(@RequestHeader("jwtToken") String jwtToken) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        UserDto userInfo = userService.getUser(userId);
        return ResponseEntity.ok(userInfo);
    }

}
