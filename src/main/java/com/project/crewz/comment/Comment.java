package com.project.crewz.comment;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {
        private int no;
        private int reviewno;
        private String memberid;
        private String content;

}
