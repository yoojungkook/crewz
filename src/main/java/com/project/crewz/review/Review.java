package com.project.crewz.review;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Review {
    private int no;
    private String memberid;
    private int moimno;
    private String content;
    private String photo;
    private String photo2;
    private String photo3;
    private int star;
}