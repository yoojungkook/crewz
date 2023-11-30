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
	//multipart 파일 경로 지정
	@Value("${spring.servlet.multipart.location}")
	private String path;
	
	//특정모임 선택
	@GetMapping("/get")
	public Map get(int moimno) {
		Map map = new HashMap();
		MoimDto m = service.get(moimno);
		map.put("m", m);
		return map;
	}
	
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
	


	
}
