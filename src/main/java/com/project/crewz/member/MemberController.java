package com.project.crewz.member;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {
    @Autowired
    private MemberService service;

    //회원가입
    @GetMapping("/member/join")
    public String joinForm() {
        return "/index/join";
    }

    @PostMapping("/member/join")
    public String join(Member m) {
        service.join(m);
        return "redirect:/";
    }

    //로그인
    @GetMapping("/member/login")
    public String loginForm(){
        return "/";
    }

    @PostMapping("/member/login")
    public String login(String id, String pwd, HttpSession session){
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
    public String getMember(String id, Model model){
        Member m = service.get(id);

        model.addAttribute("m", m);
        return "mypage/myinfo";
    }

    //마이페이지 - 내정보수정
    @PostMapping("/member/edit")
    public String editMember(Member m, Model model){
        service.edit(m);
        model.addAttribute("m", m);

        return "redirect:/myinfo/mypage?id=" + m.getId();
    }


    //로그아웃
    @GetMapping("/member/logout")
    public String logout(HttpSession session){
        session.invalidate();//서버세션
        return "redirect:/";
    }

    //회원탈퇴
    @RequestMapping("/member/delete")
    public String delMember(String id){
        service.delete(id);
        return "redirect:/";
    }

    //아이디 중복 확인
    @PostMapping("/member/checkId")
    public int idCheck(String id){
        return service.getById(id);//1을 반환하면 중복, 0이면 중복아님
    }

    @ResponseBody
    @PostMapping("/logincheck")
    public Map check(@RequestParam("id") String id, @RequestParam("pwd") String pwd) {
        Map map = new HashMap();

        Member member = service.getMember(id, pwd);

        if(member != null && member.getId() != null) {
            map.put("id", id);
        } else {
            map.put("id", null);
        }
        return map;
    }
}