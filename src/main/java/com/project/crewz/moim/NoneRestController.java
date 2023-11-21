package com.project.crewz.moim;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/moim")
public class NoneRestController {
	@Autowired
	private MoimService service;
	
	@Value("${spring.servlet.multipart.location}")
	private String path;
	
	//모임 추가
	@PostMapping("")
	public String add(MoimDto dto) {
//		System.out.println("컨트롤러 들어옴");
		
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
//			System.out.println("try 들어감");
			
			MultipartFile[] f = dto.getF();
			f[0].transferTo(newFile[0]);
			f[1].transferTo(newFile[1]);
			f[2].transferTo(newFile[2]);
			
//			System.out.println("들어감?1");
			dto.setPhoto(photo[0]);
			dto.setPhoto2(photo[1]);
			dto.setPhoto3(photo[2]);
			service.addMoim(dto);
//			System.out.println("들어감?2");
		}catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	
	@GetMapping("/{no}")
	public String get(Model model,@PathVariable("no") int no) {
		MoimDto dto = service.get(no);
		model.addAttribute("dto",dto);
		return "moim";
	}
	
}
