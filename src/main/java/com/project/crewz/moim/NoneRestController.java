package com.project.crewz.moim;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.project.crewz.somoim.SomoimDto;
import com.project.crewz.somoim.SomoimService;

@Controller
@RequestMapping("/moim")
public class NoneRestController {
	@Autowired
	private MoimService service;	
	//모임 참가 테이블 서비스
	@Autowired
	private MoimsubService ms_service;
	//소모임 테이블 서비스
	@Autowired
	private SomoimService so_service;
	//multipart 경로 지정
	@Value("${spring.servlet.multipart.location}")
	private String path;
	
	
	//모임 추가(memberid는 test고정)
	@PostMapping("")
	public String add(MoimDto dto) {
		
		//memberid 임의 지정
		dto.setMemberid("test");
		String[] photo = new String[3];
		File[] newFile = new File[3];
		for(int i = 0; i < 3; i++) {
			if(!dto.getF()[i].isEmpty()) {
				photo[i] = dto.getF()[i].getOriginalFilename();
				newFile[i] = new File(path + photo[i]);
			} else {
				photo[i] = null;
				newFile[i] = null;
			}
		}
		try {
			
			MultipartFile[] f = dto.getF();
			f[0].transferTo(newFile[0]);
			f[1].transferTo(newFile[1]);
			f[2].transferTo(newFile[2]);
			
			dto.setPhoto(photo[0]);
			dto.setPhoto2(photo[1]);
			dto.setPhoto3(photo[2]);
			service.addMoim(dto);
		}catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	//특정 모임 home화면 주소값 ex. /moim/3
	@GetMapping("/{no}")
	public String get(Model model,@PathVariable("no") int no) {
		MoimDto dto = service.get(no);
		model.addAttribute("dto",dto);
		return "moim";
	}
	
	//모임 참가, 이후 해당 모임 home화면으로 redirect
	@PostMapping("/join")
	public String joinMoim(int moimno, String memberid) {
		Moimsub ms = new Moimsub(moimno,memberid,new Date(0,0,0),new Date(0,0,0),0);
		ms_service.joinMoim(ms);
		return "redirect:/moim/"+moimno;
	}
	
	//해당 모임의 소모임들을 리스트로 반환
	@ResponseBody
	@GetMapping("/somoimlist")
	public Map getSomoim(int no) {
		Map map = new HashMap<>();
		ArrayList<SomoimDto> so_list =  so_service.getByMoim(no);
		map.put("so_list", so_list);
		
		return map;
	}
	
	//모임 수정
	@PostMapping("/eidt")
	public String edit( MoimDto dto ) {
		System.out.println(dto);
		int moimno = dto.getNo();
		//원래 사진 이름 받기
		MoimDto m = service.get(moimno);
		String og_photo = m.getPhoto();
		String og_photo2 = m.getPhoto2();
		String og_photo3 = m.getPhoto3();
		
		//받은 dto 사진 확인하기
		String photo = dto.getF()[0].getOriginalFilename();
		String photo2 = dto.getF()[1].getOriginalFilename();
		String photo3 = dto.getF()[2].getOriginalFilename();
		
		MultipartFile[] f = dto.getF();
		File file1;File file2;File file3;
		if(photo.isEmpty()) { // 비어있을 경우
			dto.setPhoto(og_photo);
			 file1 = new File(path + og_photo); 
		}else { //값이 들어온 경우
			dto.setPhoto(photo);
			 file1 = new File(path + photo);
		}
		
		if(photo2.isEmpty()) {
			dto.setPhoto2(og_photo2);
			 file2 = new File(path + og_photo2);
		}else{
			dto.setPhoto(photo2);
			 file2 = new File(path + photo2);
		}
		
		if(photo3.isEmpty()) {
			dto.setPhoto3(og_photo3);
			 file3 = new File(path + og_photo3);
		}else {
			dto.setPhoto(photo3);
			 file3 = new File(path + photo3);
		}
		try {
			f[0].transferTo(file1);
			f[1].transferTo(file2);
			f[2].transferTo(file3);
			service.editMoim(dto);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/moim/"+dto.getNo();
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
	
	//좋아요 업데이트(아직 프론트 연결 안됨)
	@ResponseBody
	@GetMapping("/changeHeart")
	public void changeHeart(int moimno) {
		MoimDto m = service.get(moimno);
		int love = m.getLove();
		//좋아요 취소 -> 
		m.setLove(--love);
		//좋아요 ->
		m.setLove(++love);
	}
	
	
}
