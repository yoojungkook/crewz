package com.project.crewz.member;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    @GetMapping("/member/get")
    public ModelAndView getMember(String id){
        ModelAndView mav = new ModelAndView("/mypage/myinfo");
        Member m = service.get(id);

        mav.addObject("m", m);
        return mav;
    }

    //마이페이지 - 내정보수정
    @PostMapping("/member/edit")
    public String editMember(Member m){
        service.edit(m);
        return "/mypage/myinfo";
    }

    //로그아웃
    @GetMapping("/member/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "/index";
    }

    //회원탈퇴
    @RequestMapping("/member/delete")
    public String delMember(String id){
        service.delete(id);
        return "/index";
    }

    //아이디 중복 확인
    @PostMapping("/member/checkId")
    public int idCheck(String id){
        return service.getById(id);//1을 반환하면 중복, 0이면 중복아님
    }
}
