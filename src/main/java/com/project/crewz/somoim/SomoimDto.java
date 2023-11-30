package com.project.crewz.somoim;

import lombok.*;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SomoimDto {
    private int no;
    private String title;
    private String content;
    private Date jdate;
    private Date mdate;
    private String loc;
    private String loc_trip;
    private int total;
    private String photo;
    private int moimno;
    private MultipartFile f;
}