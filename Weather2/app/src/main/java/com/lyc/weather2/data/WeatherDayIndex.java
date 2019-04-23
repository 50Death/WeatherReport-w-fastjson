package com.lyc.weather2.data;

import com.alibaba.fastjson.annotation.JSONField;

public class WeatherDayIndex {
    @JSONField(name = "title")
    private String title;

    @JSONField(name = "level")
    private String level;

    @JSONField(name = "desc")
    private String desc;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
