package com.project.crewz.moim;

import com.project.crewz.moim.Moimsub;
import com.project.crewz.moim.Sub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MoimsubService {
    private MoimsubDao dao;

    public ArrayList<Sub> findByMoimsub(String memberid) {
        return dao.getMoimList(memberid);
    }

    public ArrayList<Moimsub> findByMoimsubList(int moimno) {
        return dao.getMoimsubList(moimno);
    }

    //가입
    public void joinMoim(Moimsub ms) {
        dao.insert(ms);
    }

    @Autowired
    public void setMoimsubDao(MoimsubDao dao) {
        this.dao = dao;
    }
}
