package com.project.crewz.moim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoimsubService {
	@Autowired
	private MoimsubDao dao; 
	
	//가입
	public void joinMoim(Moimsub ms) {
		dao.insert(ms);
	}
	
	
	
}
