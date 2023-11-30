package com.project.crewz.category;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface CategoryDao {
    @Select("select * from category")
    ArrayList<Category> findAll();
}
