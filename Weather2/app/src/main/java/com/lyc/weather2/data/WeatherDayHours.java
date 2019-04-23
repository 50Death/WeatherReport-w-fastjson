package com.lyc.weather2.data;

import com.alibaba.fastjson.annotation.JSONField;

public class WeatherDayHours {

    @JSONField(name = "day")
    private String day;

    @JSONField(name = "wea")
    private String wea;

    @JSONField(name = "tem")
    private String tem;

    @JSONField(name = "win")
    private String win;

    @JSONField(name = "win_speed")
    private String win_speed;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getWin_speed() {
        return win_speed;
    }

    public void setWin_speed(String win_speed) {
        this.win_speed = win_speed;
    }
}
