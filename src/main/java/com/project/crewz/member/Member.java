package com.project.crewz.member;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {
    private String id;
    private String pwd;
    private String name;
    private Date birth;
    private String tel;
    private String photo;
    private String site;
}
