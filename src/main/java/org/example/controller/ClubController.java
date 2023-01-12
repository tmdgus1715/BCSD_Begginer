package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ClubDto;
import org.example.dto.requestDto.UpdateRequestDto;
import org.example.service.ClubService;
import org.example.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//클럽 삭제(멤버 수가 0명일 때만 삭제) 컨트롤러에 추가x mapper에만, 클럽 탈퇴 메소드 추가, 추방메소드 추가, 클럽멤버 리스트 조회
@RestController
@RequestMapping(value = "/clubs", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
public class ClubController {
    private final ClubService clubService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<Object> createClub(@RequestHeader("jwtToken") String jwtToken, @RequestBody ClubDto newClub) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        clubService.createClub(userId, newClub);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{clubName}")
    public ResponseEntity<Object> updateClub(@RequestHeader("jwtToken") String jwtToken, @PathVariable String clubName, @RequestBody UpdateRequestDto updateRequestDto) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        switch (updateRequestDto.getKey()) {
            case "name":
                clubService.updateName(userId, clubName, updateRequestDto.getValue());
                return ResponseEntity.ok().build();
            case "intro":
                clubService.updateIntro(userId, clubName, updateRequestDto.getValue());
                return ResponseEntity.ok().build();
            case "maxUser":
                clubService.updateMaxUser(userId, clubName, updateRequestDto.getValue());
                return ResponseEntity.ok().build();
            case "publicity":
                clubService.updatePublicity(userId, clubName, updateRequestDto.getValue());
                return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{clubName}")
    public ResponseEntity<ClubDto> getMyClubInfo(@RequestHeader("jwtToken") String jwtToken, @PathVariable String clubName) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        ClubDto clubDto = clubService.getMyClubInfo(userId, clubName);
        return ResponseEntity.ok(clubDto);
    }

    @GetMapping("/search/{clubName}")
    public ResponseEntity<Object> findJoinableClub(@PathVariable String clubName) {
        String result = clubService.findJoinableClub(clubName);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{clubName}")
    public ResponseEntity<Object> registerClub(@RequestHeader("jwtToken") String jwtToken, @PathVariable String clubName) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        clubService.registerClub(userId, clubName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{clubName}")
    public ResponseEntity<Object> withrawClub(@RequestHeader("jwtToken") String jwtToken, @PathVariable String clubName) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        clubService.withrawClub(userId, clubName);
        return ResponseEntity.ok().build();
    }
}
