package com.project.crewz.somoim;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//@RestController
//@CrossOrigin(origins="*")
@Controller
@RequestMapping("/somoim")
public class SomoimController {
	@Autowired
	private SomoimService service;
	@Autowired
	private SomoimSubService ss_service; 
	@Value("${spring.servlet.multipart.location}")
	private String path;
	
	//현재 소모임 참여자 수 반환, 실시간은 안됨
	@GetMapping("/cnt")
	@ResponseBody
	public Map currentCnt(@RequestParam Optional<Integer> somoimno) {
		Map map = new HashMap<>();
		int num = somoimno.orElse(999); // 문자 형을 숫자형으로 바꾸는 것
		int cnt = ss_service.currentCnt(num); // 소모임 참가 테이블에서 해당 소모임의 참가자 수를 받음
		map.put("cnt", cnt);
		return map; 
	}
	
	//소모임 추가
	@PostMapping("")
	public String add(SomoimDto dto) {
		String photo = dto.getF().getOriginalFilename();	
		dto.setPhoto(photo);
		MultipartFile f = dto.getF();
		File newFile = new File(path + photo);
		try {
			f.transferTo(newFile);
			service.addSomoim(dto);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}		
		return "redirect:/moim/"+dto.getMoimno();
	}
	
	//소모임 삭제, 근데 실시간으로 안 없어짐ㅠ 
	@GetMapping("/del")
	public String delSomoim(int somoimno,int moimno) {
		System.out.println("del : " + somoimno);
		service.delSomoim(somoimno);
		return "redirect:/moim/"+moimno;
	}
	
	//해당 소모임 가입
	@GetMapping("/join")
	public String joinSomim(int no, String user) {
		// 소모임참가 테이블에 존재하는 확인
		Somoimsub ss = ss_service.exist(no, user);
		
		if(ss == null) {// 없으면 새로 만들기
			ss_service.addSomoimSub(new Somoimsub(no,user,1));
		}else { // 있을 경우
			if(ss.getPartin() == 1) // 참가면 불참참으로 
				ss_service.updatePartin(new Somoimsub(no,user,0));
			else // 불참이면 참가로
				ss_service.updatePartin(new Somoimsub(no,user,1));
		}
		SomoimDto s = service.get(no);
		return "redirect:/moim/"+s.getMoimno();
	}
	
	//소모임 수정
	@PostMapping("/edit")
	public String editSomoim(SomoimDto sd) {
		System.out.println("수정 : " + sd);
		SomoimDto s = service.get(sd.getNo());
		String originalP = s.getPhoto();
		String photo = sd.getF().getOriginalFilename();	
		//만약 비있으면 기존의 photo로 
		if(photo.isEmpty()) {
			sd.setPhoto(originalP);
		}else{//비어있지 않으면 바꾸기
			sd.setPhoto(photo);
			MultipartFile f = sd.getF();
			File newFile = new File(path + photo);
			try {
				f.transferTo(newFile);
				service.editSomoim(sd);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		service.editSomoim(sd);
		return "redirect:/moim/"+sd.getMoimno();
	}
	
	//특정 소모임 선택
	@GetMapping("/get")
	@ResponseBody
	public Map getSomoim(int somoimno) {
		SomoimDto sd = service.get(somoimno);
		Map map = new HashMap();
		map.put("sd",sd);
		return map;		
	}
	
	//이미지 읽기
	@RequestMapping("/read-img")
	public ResponseEntity<byte[]> read_img(String fname) { // fname: 파일명
		System.out.println(fname);
		File f = new File(path + fname); // 파일 객체 생성
		// 응답의 헤더. 응답 페이지의 크기, 마임타입, 첨부파일..등의 정보를 갖는다.
		HttpHeaders header = new HttpHeaders();
		// ResponseEntity:응답을 데이터로 보냄
		ResponseEntity<byte[]> result = null;
		try {
			header.add("Content-Type", Files.probeContentType(f.toPath()));
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(f), header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
