package com.project.crewz.msg;

import com.project.crewz.moim.Moim;
import com.project.crewz.somoim.Somoim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MsgService {
    private MsgDao dao;

    public void insert(ArrayList<Msg> list) {
        for(Msg msg : list) {
            System.out.println("INSERT: " + msg);
            dao.insertMsg(msg);
        }
    }

    public ArrayList<Msg> myMsgList(String id, int min, int max) {
        return dao.selectMyMsg(id, min, max);
    }

    public Moim getMoim(int no) {
        return dao.selectMoim(no);
    }

    public ArrayList<Msg> mySendMsg(String id) {
        return dao.selectSendMsg(id);
    }

    public Somoim getSomoim(String id, int no) {
        return dao.selectSomoim(id, no);
    }

    @Autowired
    public void setMsgDao(MsgDao dao) {
        this.dao = dao;
    }
}
