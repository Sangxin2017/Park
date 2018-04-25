package com.ips.sx.ipark.tools;

/**
 * Created by sangx on 2018/3/10.
 */

public class ParkOpeart {
    private int  operat;
    private String date;


    public ParkOpeart(int operat, String date) {
        this.operat = operat;
        this.date = date;
    }


    public int getOperat() {
        return operat;
    }

    public void setOperat(int operat) {
        this.operat = operat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
