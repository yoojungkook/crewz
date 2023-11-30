package com.project.crewz.moim;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MoimDao {
	@Insert("insert into moim values(seq_moim.nextval, #{catno},#{memberid},#{info},#{title},#{content},#{photo}, #{photo2}, #{photo3},sysdate,#{love})")
	public void insert(Moim m);
	
	@Select("select * from moim where no=#{no}")
	public Moim select(@Param("no") int no);
	
	@Select("select * from moim order by no")
	public ArrayList<Moim> selectAll();
	
	@Update("update moim set content=#{content}, title=#{title}, info=#{info}, photo=#{photo}, photo2=#{photo2}, photo3=#{photo3} where no=#{no}")
	public void update(Moim m);
	
	@Update("udpate moim set love=#{love}")
	public void updateLove(@Param("love") int love);
	
	@Delete("delete moim where no=#{no}")
	public void delete(@Param("no") int no);
}
