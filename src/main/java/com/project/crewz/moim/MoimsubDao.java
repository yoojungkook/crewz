package com.project.crewz.moim;

import com.project.crewz.moim.Moimsub;
import com.project.crewz.moim.Sub;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface MoimsubDao {
    //가입
    @Insert("INSERT INTO MOIMSUB VALUES(#{moimno},#{memberid},sysdate,#{odate},#{black})" )
    public void insert(Moimsub ms);

    @Select("select a.no as no, a.catno as catno, a.title as title from moimsub sub, moim a where sub.moimno = a.no and sub.memberid = #{memberid}")
    ArrayList<Sub> getMoimList(@Param("memberid") String memberid);

    @Select("select memberid, black from moimsub where moimno = #{moimno}")
    ArrayList<Moimsub> getMoimsubList(@Param("moimno") int moimno);
}
