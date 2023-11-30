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
	@Insert("INSERT INTO somoim VALUES (seq_somoim.nextval, #{title}, #{content}, #{jdate}, sysdate, #{loc}, #{loc_trip}, #{total}, #{photo}, #{moimno})")
	public void insert(Somoim m );
	
	//특정 소모임 출력
	@Select("select * from somoim where no=#{no}")
	public Somoim select(@Param("no") int no);
	
	//특청 모임의 소모임들 출력
	@Select("select * from somoim where moimno=#{moimno} order by moimno")
	public ArrayList<Somoim> selectByMoim(@Param("moimno") int moimno);
	
	@Update("update somoim set title=#{title}, content=#{content}, loc=#{loc}, loc_trip=#{loc_trip},jdate=#{jdate}, total=#{total},photo=#{photo} where no=#{no}")
	public void update(Somoim m);
	
	@Delete("delete somoim where no=#{no}")
	public void delete(@Param("no") int no);
	
}
