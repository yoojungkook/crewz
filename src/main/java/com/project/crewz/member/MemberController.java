package com.project.crewz.member;

import jakarta.servlet.http.HttpServletRequest;
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
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {
    private MemberService memberService;

    private UserPool userPool;

    @Value("${spring.servlet.multipart.location}")
    private String path;

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
    @Autowired
    public void setUserPool(UserPool userPool) {
        this.userPool = userPool;
    }

    //회원가입
    @GetMapping("/member/join")
    public String joinForm() {
        return "/index/join";
    }

    /**
     * 회원가입
     * @param m
     * @return
     */
    @PostMapping("/member/join")
    public String join(Member m) {
        memberService.join(m);

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
        memberService.join(m);
        return "redirect:/";
    }

    @GetMapping("/member/login")
    public String loginForm() {
        return "/";
    }

    @PostMapping("/member/login")
    public String login(String id, String pwd, HttpSession session) {
        String path = "/index";
        Member m = memberService.get(id);

        if (m != null && pwd.equals(m.getPwd())) {
            session.setAttribute("loginId", id);
            path = "/index";
        }
        return path;
    }

    //내정보조회
    @GetMapping("/myinfo/mypage")
    public String getMember(String id, Model model) {
        Member m = memberService.get(id);

        model.addAttribute("m", m);
        return "mypage/myinfo";
    }

    //마이페이지 - 내정보수정
    @PostMapping("/member/edit")
    public String editMember(Member m, Model model){
        memberService.edit(m);
        model.addAttribute("m", m);

        return "redirect:/myinfo/mypage?id=" + m.getId();
    }


    /**
     * 로그아웃
     * @param id
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/member/logout")
    public Map logout(@RequestParam("id") String id, HttpServletRequest request){
        Map map = new HashMap();

        String browser = browserClient(request);

        boolean flag = userPool.removeSessionUser(id, browser);
        map.put("status", flag);

        return map;
    }

    //회원탈퇴
    @RequestMapping("/member/delete")
    public String delMember(String id, HttpServletRequest request) {
        if(userPool.removeSessionUser(id, browserClient(request))) {
            memberService.delete(id);
        }

        Member m = memberService.get(id);

        File dir = new File(path + m.getId());
        if(dir.exists()){
            try {
                FileUtils.deleteDirectory(dir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        memberService.delete(id);

        return "redirect:/";
    }

    //아이디 중복 확인
    @ResponseBody
    @PostMapping("/member/checkId")
    public int idCheck(@RequestParam("id") String id) {
        int count = memberService.countById(id);
        return count;
    }

    @ResponseBody
    @PostMapping("/logincheck")
    public Map check(@RequestParam("id") String id, @RequestParam("pwd") String pwd, HttpServletRequest request) {
        Map map = new HashMap();

        Member member = memberService.getMember(id, pwd);
        String browser = browserClient(request);
        System.out.println("회원: " + member);
        System.out.println("브라우저: " + browser);

        if(member != null && browser != null) {
            boolean userCheck = userPool.setSessionUser(member, browser);
            if(userCheck)
                map.put("id", id);
            else
                map.put("id", null);
        } else {
            map.put("id", null);
        }

        return map;
    }

    private String browserClient(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String checkClient = "";

        // IE
        if(userAgent.indexOf("Trident") > -1) {
            checkClient = "ie";

            // Edge
        }else if(userAgent.indexOf("Edge") > -1) {
            checkClient = "edge";

            // Naver Whale
        }else if(userAgent.indexOf("Whale") > -1) {
            checkClient = "whale";

            // Opera
        }else if(userAgent.indexOf("Opera") > -1 || userAgent.indexOf("OPR") > -1) {
            checkClient = "opera";

            // Firefox
        }else if(userAgent.indexOf("Firefox") > -1) {
            checkClient = "firefox";

            // Safari
        }else if(userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") == -1 ) {
            checkClient = "safari";

            // Chrome
        }else if(userAgent.indexOf("Chrome") > -1) {
            checkClient = "chrome";
        }

        return checkClient;
    }

    @ResponseBody
    @PostMapping("/member/temp")
    public void setMember(String id, HttpServletRequest request) {
        System.out.println("id: " + id);
        String browser = browserClient(request);
        System.out.println("request: " + browserClient(request));

        Member member = new Member();
        member.setId(id);
        userPool.setSessionUser(member, browser);
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

        memberService.editProfile(id, photoName);

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
        String result = memberService.findIdByNameNTel(name, tel);

        if (result == null) {
            result = "null";
        }
        return result;
    }

    //비밀번호 찾기
    @PostMapping("/find/pwd")
    @ResponseBody
    public String findPwd(String id, String tel) {
        String result = memberService.findPwdByIdNTel(id, tel);
        if (result == null) {
            result = "null";
        }
        return result;
    }

    @PostMapping("/join/kakao")
    @ResponseBody
    public void joinKakao(String id, String name) {
        Member m = memberService.get(id);
        Date birth = Date.valueOf("1111-11-11");
        if (m == null) {
            memberService.join(new Member(id, "null", name, birth, "null", "null", "kakao"));
        }

        Member m2 = memberService.get(id);

        File dir = new File(path + m2.getId());
        if (!(dir.exists())) {
            dir.mkdirs();
            System.out.println("폴더가 생성되었습니다.");
        } else {
            System.out.println("이미 폴더가 존재합니다.");
        }

        String copyProfile = path + "default.png";
        String pasteProfileFolder = path + m2.getId();
        String pasteProfile = pasteProfileFolder + File.separator + "default.png";

        Path copyPath = Paths.get(copyProfile);
        Path pastePath = Paths.get(pasteProfile);

        try {
            Files.copy(copyPath, pastePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}