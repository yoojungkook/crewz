package com.project.crewz.comment;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDao dao;

    public void addComment(Comment c) {
        dao.insert(c);
    }

    public void editComment(Comment c) {
        dao.update(c);
    }

    public void delComment(int no) {
        dao.delete(no);
    }
    public List<Comment> getUserComment(int reviewno){
        return dao.findOneComment(reviewno);
    }

}