package com.project.crewz.member;

import org.springframework.beans.factory.annotation.Autowired;

public class MemberService2 {
    private MemberDao dao;

    @Autowired
    public void setMemberDao(MemberDao dao) {
        this.dao = dao;
    }

    public void addMember(MemberDto dto) {
        dao.insert(new Member(dto.getId(), dto.getPwd(), dto.getName(), dto.getBirth(), dto.getTel(), dto.getPhoto(), dto.getSite()));
    }

    public MemberDto getMember(String id) {
        Member m = dao.select(id);
        if (m == null) {
            return null;
        }
        return new MemberDto(m.getId(), m.getPwd(), m.getName(), m.getBirth(), m.getTel(), m.getPhoto(), m.getSite());
    }

    public void editMember(MemberDto dto) {
        dao.update(new Member(dto.getId(), dto.getPwd(), dto.getName(), dto.getBirth(), dto.getTel(), dto.getPhoto(), dto.getSite()));
    }

    public void deleteMember(String id) {
        dao.delete(id);
    }

    public Member getMemberByIdnPwd(String id, String pwd){//todo
        return dao.selectByMember(id, pwd);
    }//todo db에 데이터를 입력할 때는 dto를 사용해야겠지만 그외의 상황에서는 이렇게 진행해도..?

    public int countById(String id){
        return dao.selectById(id);
    }

    public void editProfile(String id, String photo){
        dao.updateProfile(id, photo);
    }

    public String findIdByNameNTel(String name, String tel) {
        return dao.selectIdByNameNTel(name, tel);
    }

    public String findPwdByIdNTel(String id, String tel) {
        return dao.selectPwdByIdNTel(id, tel);
    }
}
