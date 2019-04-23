package com.lyc.weather2.data;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class WeatherDay {

    @JSONField(name = "day")
    private String day;

    @JSONField(name = "date")
    private String date;

    @JSONField(name = "week")
    private String week;

    @JSONField(name = "wea")
    private String wea;

    @JSONField(name = "wea_img")
    private String wea_img;

    @JSONField(name = "air")
    private int air;

    @JSONField(name = "humidity")
    private int humidity;

    @JSONField(name = "air_level")
    private String air_level;

    @JSONField(name = "air_tips")
    private String air_tips;

    @JSONField(name = "alarm")
    private WeatherDayAlarm alarm;

    @JSONField(name = "tem1")
    private String tem1;

    @JSONField(name = "tem2")
    private String tem2;

    @JSONField(name = "tem")
    private String tem;

    @JSONField(name = "win")
    private List win;

    @JSONField(name = "win_speed")
    private String win_speed;

    @JSONField(name = "hours")
    private List<WeatherDayHours> hours;

    @JSONField(name = "index")
    private List<WeatherDayIndex> index;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public String getWea_img() {
        return wea_img;
    }

    public void setWea_img(String wea_img) {
        this.wea_img = wea_img;
    }

    public int getAir() {
        return air;
    }

    public void setAir(int air) {
        this.air = air;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getAir_level() {
        return air_level;
    }

    public void setAir_level(String air_level) {
        this.air_level = air_level;
    }

    public String getAir_tips() {
        return air_tips;
    }

    public void setAir_tips(String air_tips) {
        this.air_tips = air_tips;
    }

    public WeatherDayAlarm getAlarm() {
        return alarm;
    }

    public void setAlarm(WeatherDayAlarm alarm) {
        this.alarm = alarm;
    }

    public String getTem1() {
        return tem1;
    }

    public void setTem1(String tem1) {
        this.tem1 = tem1;
    }

    public String getTem2() {
        return tem2;
    }

    public void setTem2(String tem2) {
        this.tem2 = tem2;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public List getWin() {
        return win;
    }

    public void setWin(List win) {
        this.win = win;
    }

    public String getWin_speed() {
        return win_speed;
    }

    public void setWin_speed(String win_speed) {
        this.win_speed = win_speed;
    }

    public List getHours() {
        return hours;
    }

    public void setHours(List hours) {
        this.hours = hours;
    }

    public List getIndex() {
        return index;
    }

    public void setIndex(List index) {
        this.index = index;
    }
}
