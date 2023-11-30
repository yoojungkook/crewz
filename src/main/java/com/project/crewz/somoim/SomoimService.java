package com.project.crewz.somoim;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SomoimService {
	@Autowired
	private SomoimDao dao;
	
	
	//소모임추가
	public void addSomoim(SomoimDto dto) {
		dao.insert(new Somoim(0,dto.getTitle(),dto.getContent(),dto.getJdate(),dto.getMdate(),dto.getLoc(),dto.getLoc_trip(),dto.getTotal(),dto.getPhoto(),dto.getMoimno()));
	}
	
	//소모임 삭제
	public void delSomoim(int no) {
		dao.delete(no);
	}
	
	//특정 모임의 소모임 리스트 출력
	public ArrayList<SomoimDto> getByMoim(int moimno){
		ArrayList<Somoim> list = dao.selectByMoim(moimno);
		ArrayList<SomoimDto> list2 = new ArrayList<>();
		for(Somoim s : list) {
			list2.add(new SomoimDto(s.getNo(),s.getTitle(),s.getContent(),s.getJdate(),s.getMdate(),s.getLoc(),s.getLoc_trip(),s.getTotal(),s.getPhoto(),s.getMoimno(),null));
		}
		return list2;
	}
	
	//특정 소모임 선택
	public SomoimDto get(int no) {
		Somoim s = dao.select(no);
		SomoimDto dto = new SomoimDto(s.getNo(),s.getTitle(),s.getContent(),s.getJdate(),s.getMdate(),s.getLoc(),s.getLoc_trip(),s.getTotal(),s.getPhoto(),s.getMoimno(),null);
		return dto;
	}
	
	//소모임 수정
	public void editSomoim(SomoimDto dto) {
		dao.update(new Somoim(dto.getNo(),dto.getTitle(),dto.getContent(),dto.getJdate(),dto.getMdate(),dto.getLoc(),dto.getLoc_trip(),dto.getTotal(),dto.getPhoto(),dto.getMoimno()));
	}
}
