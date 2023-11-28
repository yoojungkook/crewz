package com.project.crewz.comment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService service;

    @PostMapping("/add")
    public void addComment(@RequestParam(name = "reviewno") int reviewno, @RequestParam(name = "content") String content) {

        Comment c = new Comment();
        c.setReviewno(reviewno);
        c.setContent(content);
        c.setMemberid("Logged-in User");
        // 테스트용

        service.addComment(c);
    }

    @GetMapping("/comments")
    public List<Comment> getUserComment(@RequestParam(name = "reviewno") int reviewno){
        return service.getUserComment(reviewno);
    }


    // 댓글 삭제
    @DeleteMapping("/delete")
    public void deleteComment(@RequestParam(name = "no") int no) {
        service.delComment(no);
    }

    // 댓글 수정
    @PostMapping("/edit")
    public void editComment(@RequestParam(name = "no") int no, @RequestParam(name = "content") String content) {
        Comment c = new Comment();
        c.setNo(no);
        c.setContent(content);

        service.editComment(c);
    }

}
