package com.project.crewz.moim;

import java.sql.Date;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoimService {
	@Autowired
	private MoimDao  dao;
	
	//좋아요 추가
	public void changeLove(int love) {
		dao.updateLove(love);
	}
	
	//모임 추가
	public void addMoim(MoimDto dto) {
		dao.insert(new Moim(0,dto.getCatno(),dto.getMemberid(),dto.getInfo(),dto.getTitle(),dto.getContent(),dto.getPhoto(),dto.getPhoto2(),dto.getPhoto3(),new Date(0,0,0),dto.getLove()));
		 
	}
	
	//모임 삭제
	public void delMoim(int no) {
		dao.delete(no);
	}
	
	
	//모임 수정
	public void editMoim(MoimDto dto) {
		dao.update(new Moim(dto.getNo(),dto.getCatno(),dto.getMemberid(),dto.getInfo(),dto.getTitle(),dto.getContent(),dto.getPhoto(),dto.getPhoto2(),dto.getPhoto3(),new Date(0,0,0),dto.getLove()));
	}
	
	//모임 전체 리스트
	public ArrayList<MoimDto> getAll() {
		ArrayList<Moim> list = dao.selectAll();
		ArrayList<MoimDto> list2 = new ArrayList<>();
		for(Moim m : list) {
			list2.add(new MoimDto(m.getNo(),m.getCatno(),m.getMemberid(),m.getInfo(),m.getTitle(),m.getContent(),m.getPhoto(),m.getPhoto2(),m.getPhoto3(),m.getMdate(),m.getLove(), null));
		}
		return list2; 
	}
	
	//모임선택
	public MoimDto get(int num) {
		Moim m = dao.select(num);
		MoimDto dto = new MoimDto(m.getNo(),m.getCatno(),m.getMemberid(),m.getInfo(),m.getTitle(),m.getContent(),m.getPhoto(),m.getPhoto2(),m.getPhoto3(),m.getMdate(),m.getLove(),null);
		return dto;
	}

	/**
	 * 입력받은 카테고리 번호(no)의 모임 리스트
	 * @param no
	 * @return
	 */
	public ArrayList<Moim> findByCategory(int no) {
		ArrayList<Moim> list = dao.getAllCategory(no);

		return list;
	}

	/**
	 * 입력받은 카테고리 번호(no)의 모임 리스트
	 * @param no
	 * @return
	 */
	public ArrayList<Moim> findByCategoryAndTitle(int no, String title) {
		ArrayList<Moim> list = dao.getAllCategoryAndTitle(no, "%" + title + "%");

		return list;
	}

	/**
	 * 입력받은 모임의 한줄소개(info)의 모임 리스트
	 * @param info
	 * @return
	 */
	public ArrayList<Moim> findByInfo(String info) {
		ArrayList<Moim> list = dao.getAllInfo("%" + info + "%");

		return list;
	}

	/**
	 * 입력받은 모임의 타이틀(title)의 모임 리스트
	 * @param title
	 * @return
	 */
	public ArrayList<Moim> findByTitle(String title) {
		ArrayList<Moim> list = dao.getAllTitle("%" + title + "%");

		return list;
	}

	/**
	 * 입력받은 멤버의 id(memberid)의 모임 리스트
	 * @param memberid
	 * @return
	 */
	public ArrayList<Moim> findByMemberid(String memberid) {
		ArrayList<Moim> list = dao.getAllMemberid(memberid);

		return list;
	}

	/**
	 * 입력받은 모임 번호(no)의 멤버 리스트
	 * @param no
	 * @return
	 */
	public ArrayList<String> getAllMembers(int no) {
		ArrayList<String> list = dao.getMoimMember(no);

		return list;
	}
}
