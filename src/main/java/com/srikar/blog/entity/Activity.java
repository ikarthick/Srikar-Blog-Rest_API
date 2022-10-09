package com.srikar.blog.entity;

import lombok.Data;

@Data
public class Activity {

    private String activity;
    private String type;
    private Integer participants;
    private double price;
    private String link;
    private String key;
    private double accessibility;
}
