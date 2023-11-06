package com.project.crewz.somoim;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Somoim {
    private int no;
    private String title;
    private Date mdate;
    private String loc;
    private int total;
    private String photo;
    private int moimno;
}