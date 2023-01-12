package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.BoardDto;
import org.example.dto.CommentDto;
import org.example.dto.requestDto.UpdateRequestDto;
import org.example.dto.responseDto.BoardListDto;
import org.example.dto.responseDto.BoardResponseDto;
import org.example.service.BoardService;
import org.example.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/board", produces = "application/json; charset=utf8")
public class BoardController {
    private final BoardService boardService;
    private final JwtService jwtService;

    @PostMapping("{categoryName}")
    public ResponseEntity<Object> writePost(@RequestHeader("jwtToken") String jwtToken, @PathVariable String categoryName, @RequestBody BoardDto newPost) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        boardService.writePost(categoryName, userId, newPost);
        return ResponseEntity.created(new URI("http://localhost:8080/board/list")).build();
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Object> updatePost(@RequestHeader("jwtToken") String jwtToken, @PathVariable Integer postId, @RequestBody UpdateRequestDto updateRequestDto) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        switch (updateRequestDto.getKey()) {
            case "title":
                boardService.updateTitle(userId, postId, updateRequestDto.getValue());
                return ResponseEntity.ok().build();
            case "content":
                boardService.updateContent(userId, postId, updateRequestDto.getValue());
                return ResponseEntity.ok().build();
            case "trainingLog":
                boardService.updateTrainingLog(userId, postId, updateRequestDto.getValue());
                return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Object> deletePost(@RequestHeader("jwtToken") String jwtToken, @PathVariable Integer postId) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        boardService.deletePost(userId, postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Object> recommendPost(@PathVariable Integer postId) {
        boardService.recommendPost(postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}")//게시물 내용 조회
    public ResponseEntity<BoardResponseDto> getPost(@PathVariable Integer postId) {
        BoardResponseDto boardResponseDto = boardService.getPost(postId);
        return ResponseEntity.ok(boardResponseDto);
    }

    @GetMapping("/list")//유저의 게시물 목록 조회
    public ResponseEntity<List<BoardListDto>> getUserPosts(@RequestHeader("jwtToken") String jwtToken) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        List<BoardListDto> boardListDtos = boardService.getUserPosts(userId);
        return ResponseEntity.ok(boardListDtos);
    }

    @GetMapping({"{categoryName}/list"})// way는 asc, dsc
    public ResponseEntity<List<BoardListDto>> getPostsOderByDate(@PathVariable String categoryName, HttpServletRequest way) {
        List<BoardListDto> boardListDtos = boardService.getPostsOrderByDate(categoryName, way.getParameter("way"));
        return ResponseEntity.ok(boardListDtos);
    }

    @GetMapping("{categoryName}/list/like")
    public ResponseEntity<List<BoardListDto>> getPostsOrderByLike(@PathVariable String categoryName) {
        List<BoardListDto> boardListDtos = boardService.getPostsOrderByLike(categoryName);
        return ResponseEntity.ok(boardListDtos);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<Object> leaveComment(@PathVariable Integer postId, @RequestHeader("jwtToken") String jwtToken, @RequestBody String comment) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        boardService.leaveComment(postId, userId, comment);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Integer postId) {
        List<CommentDto> commentDtos = boardService.getComments(postId);
        return ResponseEntity.ok(commentDtos);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Object> deleteComment(@PathVariable Integer postId, @RequestHeader("jwtToken") String jwtToken, @PathVariable Integer commentId) throws Exception {
        Integer userId = jwtService.getUserId(jwtToken);
        boardService.deleteComment(postId, userId, commentId);
        return ResponseEntity.ok().build();
    }

}
