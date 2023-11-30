package com.project.crewz.msg;

import com.project.crewz.moim.Moim;
import com.project.crewz.somoim.Somoim;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface MsgDao {
    @Insert("INSERT INTO MSG VALUES(#{no}, #{div}, #{smemberid}, #{pmemberid}, #{title}, #{content}, sysdate, 0)")
    void insertMsg(Msg msg);

    @Select("select ROWNUM, m.* from msg m where  pmemberid = #{id} and smemberid != pmemberid and ROWNUM BETWEEN #{min} AND #{max} ORDER BY MDATE DESC")
    ArrayList<Msg> selectMyMsg(@Param("id") String id, @Param("min") int min, @Param("max") int max);

    @Select("select distinct moim.* from msg msg, moim moim where msg.no = moim.no and moim.no = #{no}")
    Moim selectMoim(@Param("no") int no);

    @Select("select distinct msg.* from msg msg, moim moim where msg.no = moim.no and smemberid = #{id}")
    ArrayList<Msg> selectSendMsg(@Param("id") String id);

    @Select("select distinct m.* from msg msg, somoim m where msg.no = m.no and msg.div = 1 and msg.smemberid = #{id} and m.no = #{no}")
    Somoim selectSomoim(@Param("id") String id, @Param("no") int no);
}