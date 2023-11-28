package com.project.crewz.moim;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@CrossOrigin(origins="*")
@RequestMapping("/moim")
public class MoimController {
	@Autowired
	private MoimService service;
	
	@Value("${spring.servlet.multipart.location}")
	private String path;
	
	//모임 추가
	
	//모든 모임 출력
	@GetMapping("")
	public Map list() {
		Map map = new HashMap();
		ArrayList<MoimDto> list = service.getAll();
		map.put("list", list);
		return map;
	}
	
	//모임 삭제
	@DeleteMapping("/{no}")
	public void del(@PathVariable("no") int no) {
		service.delMoim(no);
		
	}
	
//	//특정 모임 가져오기
//	@GetMapping("/{no}")
//	public Map get(@PathVariable("no") int no) {
//		Map map = new HashMap();
//		MoimDto dto = service.get(no);
//		map.put("dto", dto);
//		return map;
//	}
	
	//모임 수정
	@PutMapping("")
	public void edit(@RequestHeader("token") String token, MoimDto dto ) {
		service.editMoim(dto);
	}
	
	
//	@RequestMapping("/read-img")
//	public ResponseEntity<byte[]> read_img(String fname) {
//	    File f = new File(path + fname);
//
//	    HttpHeaders header = new HttpHeaders();
//
//	    try {
//	        // URL 인코딩을 수행하여 URI를 생성
//	        String encodedFname = UriComponentsBuilder.fromPathSegment(fname).build().encode().toUriString();
//	        header.add("Content-Type", Files.probeContentType(f.toPath()));
//	        ResponseEntity<byte[]> result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(f), header, HttpStatus.OK);
//	        return result;
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}
//	
	
	//사진 모여주기
	// 파일명을 파람으로 받아서 파일 내용을 복사하여 바이너리 형태의 응답으로 보내줌
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
