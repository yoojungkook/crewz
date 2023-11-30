package com.project.crewz.comment;




import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface CommentDao {

    @Select("SELECT * FROM \"HONG\".\"COMMENTT\" WHERE reviewno = ${reviewno} ORDER BY cdate ASC ")
    List<Comment> findOneComment(@Param("reviewno") int reviewno);

    @Insert("insert into \"HONG\".\"COMMENTT\" values(COMMENTT_SEQ.NEXTVAL, #{reviewno}, #{memberid}, #{content}, sysdate)")
    void insert(Comment c);

    @Update("update \"HONG\".\"COMMENTT\" set content = #{content} where no = #{no}")
    void update(Comment c);

    @Delete("delete from \"HONG\".\"COMMENTT\" where no=#{no}")
    void delete(@Param("no") int no);
}
