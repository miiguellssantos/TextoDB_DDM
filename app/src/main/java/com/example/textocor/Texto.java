package com.example.textocor;

import android.graphics.Color;

public class Texto {

    private int id;
    private String text;
    private String color;

    public Texto(int id, String text, String color) {
        this.id = id;
        this.text = text;
        this.color = color;
    }

    public Texto(String text, String color) {
        this.text = text;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
