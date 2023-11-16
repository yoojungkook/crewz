package com.project.crewz.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private MemberDao dao;

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

    public int getById(String id){
        return dao.selectById(id);
    }
}
