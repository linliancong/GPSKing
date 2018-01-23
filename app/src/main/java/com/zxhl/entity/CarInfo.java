package com.zxhl.entity;

/**
 * Created by Administrator on 2018/1/18.
 */

public class CarInfo {
<<<<<<< HEAD
=======
    private String vehicle_title;
>>>>>>> e688b9f5c58f008c610046dcb089e12ac2c1eb2c
    private String vehicle;
    private String time_title;
    private String time;
    private String info_title;
    private String info;

<<<<<<< HEAD
    public CarInfo(String vehicle,String info,String time,String time_title,String info_title){
=======
    public CarInfo(String vehicle,String info,String time,String time_title,String info_title,String vehicle_title){
        this.vehicle_title=vehicle_title;
>>>>>>> e688b9f5c58f008c610046dcb089e12ac2c1eb2c
        this.vehicle=vehicle;
        this.time_title=time_title;
        this.time=time;
        this.info_title=info_title;
        this.info=info;
    }

<<<<<<< HEAD
=======
    public void setVehicle_title(String vehicle_title) {
        this.vehicle_title = vehicle_title;
    }

>>>>>>> e688b9f5c58f008c610046dcb089e12ac2c1eb2c
    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public void setTime_title(String time_title) {
        this.time_title = time_title;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setInfo_title(String info_title) {
        this.info_title = info_title;
    }

    public void setInfo(String info) {
        this.info = info;
    }

<<<<<<< HEAD
=======
    public String getVehicle_title() {
        return vehicle_title;
    }

>>>>>>> e688b9f5c58f008c610046dcb089e12ac2c1eb2c
    public String getVehicle() {
        return vehicle;
    }

    public String getTime_title() {
        return time_title;
    }

    public String getTime() {
        return time;
    }

    public String getInfo() {
        return info;
    }

    public String getInfo_title() {
        return info_title;
    }
}
