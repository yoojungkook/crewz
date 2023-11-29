package com.project.crewz.review;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Review {
    private int no;
    private int SOMOIMNO;
    private String memberid;
    private String categoryno;
    private String title;
    private String content;

    private String filename1;
    private String filename2;
    private String filename3;

    private MultipartFile photo1;
    private MultipartFile photo2;
    private MultipartFile photo3;

    private Date mdate;

}