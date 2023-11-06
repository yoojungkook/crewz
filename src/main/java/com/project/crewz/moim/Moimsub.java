package com.project.crewz.moim;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Moimsub {
    private int moimno;
    private String memberid;
    private Date idate;
    private Date odate;
    private int black;
}