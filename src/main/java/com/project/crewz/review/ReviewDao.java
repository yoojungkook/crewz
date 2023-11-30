package com.project.crewz.review;

import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface ReviewDao {
    @Insert("insert into \"HONG\".\"TEST\" values(TEST_SEQ.NEXTVAL, #{memberid}, #{categoryno}, #{title}, #{content},#{filename1,jdbcType=VARCHAR},#{filename2,jdbcType=VARCHAR},#{filename3,jdbcType=VARCHAR}, sysdate, null)")
    void insert(Review r);

    @Select("select TEST_SEQ.NEXTVAL from dual")
    int getNextNo();
    
    @Select("select * from \"HONG\".\"TEST\" where no=#{no}")
    Review select(@Param("no") int no);

    @Update("update \"HONG\".\"TEST\" set categoryno = #{categoryno}, title = #{title}, content = #{content}, filename1 = #{filename1,jdbcType=VARCHAR}, filename2 = #{filename2,jdbcType=VARCHAR}, filename3 = #{filename3,jdbcType=VARCHAR} where no = #{no}")
    void update(Review r);

    @Delete("delete from \"HONG\".\"TEST\" where no=#{no}")
    void delete(@Param("no") int no);

    @Select("select * from \"HONG\".\"TEST\"")
    ArrayList<Review> selectAll();
}

