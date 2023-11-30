package com.project.crewz.member;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserPool {
    @Bean
    public List<Profile> sessionUser() {
        List<Profile> list = new ArrayList<>();
        return list;
    };

    public UserPool() {
        System.out.println("UserPool 생성!");
    }

    /**
     * sessionUser에 로그인한 유저 추가, 이미 접속한 유저라면 로그인 되게 유도
     * @param member
     * @return
     */
    public boolean setSessionUser(Member member, String browser) {
        boolean flag = false;
        int status = checkUserStatus(member.getId(), browser);
        if (status == -1) {
            Profile profile = new Profile();
            profile.setMember(member);
            profile.setBrowser(browser);

            sessionUser().add(profile);
            System.out.println("로그인 계정: " + profile);
            flag = true;
        }

        return flag;
    }

    /**
     * 특정 유저(및 브라우저)를 반환
     * @param id
     * @return
     */
    public Member getSessionUser(String id, String browser) {
        Member member = null;
        int userNumber = checkUserStatus(id, browser);
        member = sessionUser().get(userNumber).getMember();

        return member;
    }

    /**
     * 로그인 된 모든 유저를 반환
     * @return
     */
    public List<Profile> getSessionUserList() {
        return sessionUser().stream().toList();
    }

    /**
     * 로그아웃 or 회원탈퇴시 저장된 유저를 삭제
     * @param id
     */
    public boolean removeSessionUser(String id, String browser) {
        boolean flag = false;
        int userNumber = checkUserStatus(id, browser);
        if(userNumber != -1) {
            flag = true;
            System.out.println("로그아웃 or 회원탈퇴 계정: " + sessionUser().get(userNumber));
            sessionUser().remove(userNumber);
        }

        return flag;
    }

    /**
     * 각 상황에 맞는 번호 반환
     * @param id
     * @param browser
     * @return
     */
    public int checkUserStatus(String id, String browser) {
        int getNumber = -1;
        for (int i = 0; i < sessionUser().size(); i++) {
            System.out.println(sessionUser().get(i));
            if (sessionUser().get(i).getMember().getId().equals(id)) {
                if (sessionUser().get(i).getBrowser().equals(browser)) {
                    getNumber = i;
                    break;
                }
            }
        }

        return getNumber;
    }
}
