package com.project.crewz.member;

import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {
    @Autowired
    private MemberService service;

    @Value("${spring.servlet.multipart.location}")
    private String path;

    //회원가입
    @GetMapping("/member/join")
    public String joinForm() {
        return "/index/join";
    }

    @PostMapping("/member/join")
    public String join(Member m) {
        service.join(m);

        File dir = new File(path + m.getId());
        if (!(dir.exists())) {
            dir.mkdirs();
            System.out.println("폴더가 생성되었습니다.");
        } else {
            System.out.println("이미 폴더가 존재합니다.");
        }

        String copyProfile = path + "default.png";
        String pasteProfileFolder = path + m.getId();
        String pasteProfile = pasteProfileFolder + File.separator + "default.png";

        Path copyPath = Paths.get(copyProfile);
        Path pastePath = Paths.get(pasteProfile);

        try {
            Files.copy(copyPath, pastePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }

    //로그인
    @GetMapping("/member/login")
    public String loginForm() {
        return "/";
    }

    @PostMapping("/member/login")
    public String login(String id, String pwd, HttpSession session) {
        String path = "/index";
        Member m = service.get(id);

        if (m != null && pwd.equals(m.getPwd())) {
            session.setAttribute("loginId", id);
            path = "/index";
        }
        return path;
    }

    //내정보조회
    @GetMapping("/myinfo/mypage")
    public String getMember(String id, Model model) {
        Member m = service.get(id);

        model.addAttribute("m", m);
        return "mypage/myinfo";
    }

    //마이페이지 - 내정보수정
    @PostMapping("/member/edit")
    public String editMember(Member m, Model model) {
        service.edit(m);
        model.addAttribute("m", m);

        return "redirect:/myinfo/mypage?id=" + m.getId();
    }


    //로그아웃
    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        return "redirect:/";
    }

    //회원탈퇴
    @RequestMapping("/member/delete")
    public String delMember(String id) {
        service.delete(id);
        return "redirect:/";
    }

    //아이디 중복 확인
    @ResponseBody
    @PostMapping("/member/checkId")
    public int idCheck(@RequestParam("id") String id) {
        int count = service.countById(id);
        return count;
    }

    @ResponseBody
    @PostMapping("/logincheck")
    public Map check(@RequestParam("id") String id, @RequestParam("pwd") String pwd) {
        Map map = new HashMap();

        Member member = service.getMember(id, pwd);

        if (member != null && member.getId() != null) {
            map.put("id", id);
        } else {
            map.put("id", null);
        }
        return map;
    }

    @PostMapping("/profile")//프로필사진 업로드
    public String profile(String id, MultipartFile photo) throws IOException {
        //기존 폴더에 파일이 있다면 삭제
        String existDir = path + File.separator + id;
        File existDir2 = new File(existDir);
        if (!existDir.isEmpty()) {
            FileUtils.cleanDirectory(existDir2);
        }

        //사진명
        String photoName = photo.getOriginalFilename();
        //photoDir에 파일의 경로를 담는다
        File photoDir = new File(path + File.separator + id + File.separator + photoName);
        //업로드한 파일은 photoDir로 이동
        photo.transferTo(photoDir);

        service.editProfile(id, photoName);

        return "redirect:/myinfo/mypage?id=" + id;
    }

    @RequestMapping("/profile/read")
    public ResponseEntity<byte[]> readProfile(String id, String photo) {
        //profile 객체 생성
        File profile = null;
        if (!photo.equals("zero"))
            profile = new File(path + File.separator + id + File.separator + photo);
        else
            profile = new File(path + File.separator + id + File.separator + "default.png");
        HttpHeaders header = new HttpHeaders();
        ResponseEntity<byte[]> result = null;
        try {
            header.add("Content-Type", Files.probeContentType(profile.toPath()));
            result = new ResponseEntity<byte[]>
                    (FileCopyUtils.copyToByteArray(profile), header, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    //아이디 찾기
    @PostMapping("/find/id")
    @ResponseBody
    public String findId(String name, String tel) {
        String result = service.findIdByNameNTel(name, tel);

        if (result == null) {
            result = "null";
        }
        return result;
    }

    //비밀번호 찾기
    @PostMapping("/find/pwd")
    @ResponseBody
    public String findPwd(String id, String tel) {
        String result = service.findPwdByIdNTel(id, tel);
        if (result == null) {
            result = "null";
        }
        return result;
    }
}