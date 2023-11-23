package com.project.crewz.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private MemberDao dao;

    @Autowired
    public void setMemberDao(MemberDao dao) {
        this.dao = dao;
    }

    public Member getMember(String id, String pwd) {
        return dao.selectByMember(id, pwd);
    }

    public void join(Member m) {
        dao.insert(m);
    }

    public Member get(String id) {
        return dao.select(id);
    }

    public void edit(Member m) {
        dao.update(m);
    }

    public void delete(String id) {
        dao.delete(id);
    }

    public int countById(String id) {
        return dao.selectById(id);
    }

    public void editProfile(String id, String photo) {
        dao.updateProfile(id, photo);
    }
}
