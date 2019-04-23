package com.lyc.weather2.data;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;
import java.util.Vector;

public class Weather {

    @JSONField(name = "cityid")
    private String cityid;

    @JSONField(name = "update_time")
    private String update_time;

    @JSONField(name = "city")
    private String city;

    @JSONField(name = "cityEn")
    private String cityEn;

    @JSONField(name = "country")
    private String country;

    @JSONField(name = "countryEn")
    private String countryEn;

    @JSONField(name = "data")
    private List<WeatherDay> data;



    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
