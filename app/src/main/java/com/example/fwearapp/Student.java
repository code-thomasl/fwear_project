package com.example.fwearapp;

public class Student {
    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public float getGps_lat() {
        return gps_lat;
    }

    public void setGps_lat(float gps_lat) {
        this.gps_lat = gps_lat;
    }

    public float getGps_long() {
        return gps_long;
    }

    public void setGps_long(float gps_long) {
        this.gps_long = gps_long;
    }

    public String getStudent_message() {
        return student_message;
    }

    public void setStudent_message(String student_message) {
        this.student_message = student_message;
    }

    private int student_id;
    private float gps_lat;
    private float gps_long;
    private String student_message;

    public Student(int student_id, float gps_lat, float gps_long, String student_message) {
        this.student_id = student_id;
        this.gps_lat = gps_lat;
        this.gps_long = gps_long;
        this.student_message = student_message;
    }


}
