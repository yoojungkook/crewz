package com.project.crewz;

import com.project.crewz.member.Profile;
import com.project.crewz.member.UserPool;
import com.project.crewz.moim.Moimsub;
import com.project.crewz.msg.Msg;
import com.project.crewz.moim.MoimsubService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotificationController {
    private MoimsubService moimsubService;
    private UserPool userPool;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/moim")
    public void greet(@RequestBody Msg msg) throws InterruptedException {
        System.out.println("WS 진입!");
        System.out.println(msg);

        List<Profile> list = userPool.getSessionUserList();
        if(msg.getDiv() == 0) {
            ArrayList<Moimsub> moimsubList = moimsubService.findByMoimsubList(msg.getNo());
            System.out.println("모임 가입 이력자");
            for(Moimsub m : moimsubList) {
                System.out.println(m);
            }

            for(Profile p : list) {
                simpMessagingTemplate.convertAndSend("/sub/message/" + p.getMember().getId(), msg);
            }
        }
//        Thread.sleep(500);
        System.out.println("WS 탈출!");
    }

    @Autowired
    public void setMoimsubService(MoimsubService moimsubService) {
        this.moimsubService = moimsubService;
    }

    @Autowired
    public void setUserPool(UserPool userPool) {
        this.userPool = userPool;
    }
}
