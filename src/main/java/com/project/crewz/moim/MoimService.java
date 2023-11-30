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
		//System.out.println("serviced에서 : " + dto);
		dao.update(new Moim(dto.getNo(),dto.getCatno(),dto.getMemberid(),dto.getInfo(),dto.getTitle(),dto.getContent(),dto.getPhoto(),dto.getPhoto2(),dto.getPhoto3(),new Date(0,0,0),dto.getLove()));
	}
	
	//모임 전체 리스트(이후 index페이지에서 쓸 예정)
	public ArrayList<MoimDto> getAll() {
		ArrayList<Moim> list = dao.selectAll();
		ArrayList<MoimDto> list2 = new ArrayList<>();
		for(Moim m : list) {
			list2.add(new MoimDto(m.getNo(),m.getCatno(),m.getMemberid(),m.getInfo(),m.getTitle(),m.getContent(),m.getPhoto(),m.getPhoto2(),m.getPhoto3(),m.getMdate(),m.getLove(), null));
		}
		return list2; 
	}
	
	//특정 모임 선택
	public MoimDto get(int num) {
		Moim m = dao.select(num);
		MoimDto dto = new MoimDto(m.getNo(),m.getCatno(),m.getMemberid(),m.getInfo(),m.getTitle(),m.getContent(),m.getPhoto(),m.getPhoto2(),m.getPhoto3(),m.getMdate(),m.getLove(),null);
		return dto;
	}
	
}
