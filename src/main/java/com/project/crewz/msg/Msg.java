package com.project.crewz.msg;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Msg {
    private int no;
    private int div;
    private String smemberid;
    private String pmemberid;
    private String title;
    private String content;
    private Date mdate;
    private int read;
}
