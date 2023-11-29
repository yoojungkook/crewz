package com.project.crewz.moim;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.project.crewz.somoim.Somoimsub;

@Mapper
public interface MoimsubDao {

	//가입
	@Insert("INSERT INTO MOIMSUB VALUES(#{moimno},#{memberid},sysdate,#{odate},#{black})" )
	public void insert(Moimsub ms);
	
	
}
