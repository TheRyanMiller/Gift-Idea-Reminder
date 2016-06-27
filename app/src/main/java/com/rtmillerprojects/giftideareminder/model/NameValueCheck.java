package com.rtmillerprojects.giftideareminder.model;

/**
 * Created by Ryan on 6/12/2016.
 */
public class NameValueCheck {
    public String name;
    public int value;
    public boolean isChecked;
    public NameValueCheck(){}
    public NameValueCheck(String name, int value){
        this.name = name;
        this.value = value;
        this.isChecked = false;
    }
    public NameValueCheck(String name, int value, boolean isChecked){
        this.name = name;
        this.value = value;
        this.isChecked = isChecked;
    }
}
