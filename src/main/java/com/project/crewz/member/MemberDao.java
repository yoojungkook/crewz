package com.project.crewz.member;

import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberDao {
    @Insert("INSERT INTO member VALUES(#{id}, #{pwd}, #{name}, #{birth}, #{tel}, #{photo}, #{site})")
    void insert(Member m);

    @Select("SELECT id, pwd FROM member WHERE id = #{id} AND pwd = #{pwd}")
    Member selectByMember(@Param("id") String id, @Param("pwd") String pwd);

    @Select("SELECT * FROM member WHERE id=#{id}")
    Member select(@Param("id") String id);

    @Update("UPDATE member SET pwd=#{pwd}, tel = #{tel}, photo = #{photo} WHERE id=#{id}")
    void update(Member m);

    @Delete("DELETE FROM member WHERE id=#{id}")
    void delete(@Param("id") String id);

    @Select("SELECT count(*) From member WHERE id=#{id}")
    int selectById(@Param("id") String id);
}
