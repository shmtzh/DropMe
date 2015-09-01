package com.example.shmtzh.dropme.DataModels;


public class HistoryModel {
    private double x;
    private double y;
    private double z;
//    private CharSequence date;

//    public HistoryModel(double x, double y, double z, CharSequence date) {
//        this.x = x;
//        this.y = y;
//        this.z = z;
//        this.date = date;
//    }


    public HistoryModel(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
//
//    public CharSequence getDate() {
//        return date;
//    }
//
//    public void setDate(CharSequence date) {
//        this.date = date;
//    }
}
