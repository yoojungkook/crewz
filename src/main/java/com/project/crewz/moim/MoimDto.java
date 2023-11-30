package com.project.crewz.moim;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MoimDto {
   
	private int no;
    private int catno;
    private String memberid;
    private String info;
    private String title;
    private String content;
    private String photo;
    private String photo2;
    private String photo3;
    private Date mdate;
    private int love;
    private MultipartFile[] f;
}
