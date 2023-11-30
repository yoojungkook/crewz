package com.project.crewz.somoim;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface SomoimSubDao {
	//소모임 참가
	@Insert("INSERT INTO SOMOIMSUB VALUES(#{somoimno},#{memberid},#{partin})" )
	public void insert(Somoimsub ss);
	
	//참가 or 참가 취소
	@Update("update somoimsub set partin=#{partin} where somoimno =#{somoimno} and memberid=#{memberid} ")
	public void updatePartin(Somoimsub ss);
	
	//해당 소모임 현재 인원 출력
	@Select("select count(*) from somoimsub where somoimno=#{somoimno} and partin=1")
	public int count(@Param("somoimno") int somoimno);
	
	//해당 맴버가 그 소모임에 참가 했는지 안했는지
	@Select("select * from somoimsub where somoimno=#{somoimno} and memberid=#{memberid}")
	public Somoimsub exist(@Param("somoimno") int somoimno, @Param("memberid") String memberid);
}
