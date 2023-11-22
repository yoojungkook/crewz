package com.project.crewz.review;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewDao dao;

    public ArrayList<Review> getAll() {
        return dao.selectAll();
    }

    public Review getReview(int no) {
        return dao.select(no);
    }

    public void addReview(Review r) {
        dao.insert(r);
    }

    public void editReview(Review r) {
        dao.update(r);
    }

    public void delReview(int no) {
        dao.delete(no);
    }


}
