package com.project.crewz.somoim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SomoimSubService {
	@Autowired
	private SomoimSubDao dao;
	
	//참가 삽입
	public void addSomoimSub(Somoimsub ss) {
		dao.insert(ss);
	}
	
	//참가 수정
	public void updatePartin(Somoimsub ss) {
		dao.updatePartin(ss);
	}
	
	//해당 소모임에 현재 참가 인원
	public int currentCnt(int somoimno) {
		int cnt = dao.count(somoimno);
		return cnt;
	}
	
	//해당 소모임 참가자가 테이블에 있는지 없는지
	public Somoimsub exist(int somoinno, String memberid) {
		Somoimsub result = dao.exist(somoinno, memberid);
		return result;
	}
	
}
