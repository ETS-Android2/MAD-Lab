package com.sp.listofeeecourses;

import android.widget.ImageView;

public class Course {
    private String strName = "";
    private String strDesc = "";
    private ImageView pic;

    public Course(String name, String desc){
        strDesc = desc;
        strName = name;
    }

    public String getName(){
        return strName;
    }

    public String getDesc(){
        return strDesc;
    }

    public String getCourseID(){
        return strName.substring(strName.indexOf('(') + 1, strName.indexOf(')'));
    }

    @Override
    public String toString() {
        return (getName());
    }
}
