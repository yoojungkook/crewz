package com.project.crewz.somoim;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SomoimDao {
	@Insert("insert into somoim values(seq_somoim.nextval,#{title},#{jdate},#{mdate},#{loc},#{total},#{photo},#{moimno}")
	public void insert(Somoim m );
	
	@Select("select * from somoim where no=#{no}")
	public Somoim select(@Param("no") int no);
	
	@Select("select * from somoim")
	public ArrayList<Somoim> selectAll();
	
	@Update("update somoim set loc=#{loc}, jdate=#{jdate} where no=#{no}")
	public void update(Somoim m);
	
	@Delete("delete somoim where no=#{no}")
	public void delete(@Param("no") int no);
	
}
