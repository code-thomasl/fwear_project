package com.example.fwearapp;

public class Message {

/*
*
* [
  {
    "id": 17,
    "student_id": 20130039,
    "gps_lat": 36.001,
    "gps_long": 3.235,
    "student_message": "message3"
  },
* */

private int id;
private int student_id;
private float gps_lat;
private float gps_long;
private String student_message;


    public int getStudent_id() {
        return student_id;
    }

    public float getGps_lat() {
        return gps_lat;
    }

    public float getGps_long() {
        return gps_long;
    }

    public String getStudent_message() {
        return student_message;
    }

    public int getId() {
        return id;
    }
}
