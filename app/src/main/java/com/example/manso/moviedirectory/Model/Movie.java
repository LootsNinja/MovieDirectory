package com.example.manso.moviedirectory.Model;

import java.io.Serializable;

public class Movie implements Serializable{

    private String title;
    private String type;
    private String posterURL;
    private String year;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }



}
