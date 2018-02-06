package com.example.haozhengqin.assignment1;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by haozhengqin on 2018/2/5.
 */

public class information {
    private String name;
    private Date date;
    private String comment;
    private int value;

    public information(String name, int value) {
        this.name = name;
        this.value = value;
        this.date = new Date();
    }

    public information(String name, String comment, int value) {
        this.name = name;
        this.comment = comment;
        this.value = value;

        this.date = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public void setDate() {
        this.date = new Date();
    }

    public void delete(){
        this.value = 0;
        this.comment = "";
        this.name = "";
    }
    @Override
    public String toString() {
        String d = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        return "Name:"+ name + ", Monthly charge:" + value + ", date:" + d + ", " + comment;

    }

}