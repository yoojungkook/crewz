package com.project.crewz.somoim;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//@RestController
//@CrossOrigin(origins="*")
@Controller
@RequestMapping("/somoim")
public class SomoimController {
	@Autowired
	private SomoimService service;
	
	@Value("${spring.servlet.multipart.location}")
	private String path;
	
	//모임 추가
	@PostMapping("")
	public String add(SomoimDto dto) {
		System.out.println("소모임 컨트롤러 들어옴");
		System.out.println(dto.getTitle()+dto.getMoimno());
		String photo = dto.getF().getOriginalFilename();
		MultipartFile f = dto.getF();
		
		File newFile = new File(path + photo);
		try {
			f.transferTo(newFile);
			service.addSomoim(dto);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/moim/"+dto.getMoimno();
		
	}
	
}
