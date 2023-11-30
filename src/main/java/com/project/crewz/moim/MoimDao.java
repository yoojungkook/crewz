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
	
	@Update("update moim set content=#{content}, title=#{title} where no=#{no}")
	public void update(Moim m);
	
	@Update("udpate moim set love=#{love}")
	public void updateLove(@Param("love") int love);
	
	@Delete("delete moim where no=#{no}")
	public void delete(@Param("no") int no);

	/* Jeongguk */
	@Select("select * from moim where catno = #{no}")
	ArrayList<Moim> getAllCategory(@Param("no") int no);

	@Select("select * from moim where catno = #{no} and title like #{title}")
	ArrayList<Moim> getAllCategoryAndTitle(@Param("no") int no, @Param("title") String title);

	@Select("select * from moim where info like #{info}")
	ArrayList<Moim> getAllInfo(@Param("info") String info);

	@Select("select * from moim where title like #{title}")
	ArrayList<Moim> getAllTitle(@Param("title") String title);

	@Select("select * from moim where memberid = #{memberid}")
	ArrayList<Moim> getAllMemberid(@Param("memberid") String memberid);

	@Select("select memberid from moim where no = #{no}")
	ArrayList<String> getMoimMember(int no);
}
