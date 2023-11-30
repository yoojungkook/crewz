package com.project.crewz.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {
    private CategoryDao dao;

    public ArrayList<Category> getAll() {
        return dao.findAll();
    }

    @Autowired
    public void setCategoryDao(CategoryDao dao) {
        this.dao = dao;
    }
}
