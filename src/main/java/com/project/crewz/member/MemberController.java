package com.project.crewz.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {
    private MemberService memberService;

    private UserPool userPool;

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
        return "redirect:/";
    }

    @GetMapping("/member/login")
    public String loginForm(){
        return "/";
    }

    @PostMapping("/member/login")
    public String login(String id, String pwd, HttpSession session){
        String path = "/index";
        Member m = memberService.get(id);

        if (m != null && pwd.equals(m.getPwd())) {
            session.setAttribute("loginId", id);
            path = "/index";
        }
        return path;
    }

    //내정보조회
    @RequestMapping("/myinfo/mypage")
    public String getMember() {
//    public String getMember(String id, HttpServletRequest request, Model model){
//        String browser = browserClient(request);
//        Member m1 = userPool.getSessionUser(id, browser);
//
//        Member m2 = memberService.get(id);
//
//        if(m1.getId().equals(m2.getId())) {
//            model.addAttribute("m", memberService.get(id));
//            return "mypage/info";
//        }
//
//        return "/";
        return "mypage/info";
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
    public String delMember(String id, HttpServletRequest request){
        if(userPool.removeSessionUser(id, browserClient(request))) {
            memberService.delete(id);
        }
        return "redirect:/";
    }

    //아이디 중복 확인
    @PostMapping("/member/checkId")
    public int idCheck(String id){
        return memberService.getById(id);//1을 반환하면 중복, 0이면 중복아님
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
}