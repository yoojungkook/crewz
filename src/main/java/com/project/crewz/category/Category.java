package com.project.crewz.category;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category {
    private int no;
    private String name;
    private String photo;
    private int total;
}