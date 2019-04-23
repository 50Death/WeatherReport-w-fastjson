package com.lyc.weather2.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherJSON {
    private Weather weather;

    public WeatherJSON(String jsonText) {
        weather = JSON.parseObject(jsonText, Weather.class);
        List<WeatherDay> weatherDayList = new ArrayList<WeatherDay>();
        for (Object o : weather.getData()) {
            WeatherDay wd = JSONObject.parseObject(((JSONObject) o).toJSONString(), WeatherDay.class);
            //WeatherDayAlarm wda = JSONObject.parseObject(((JSONObject) wd.getAlarm()).toJSONString(),WeatherDayAlarm.class);

            List<WeatherDayHours> weatherDayHoursList = new ArrayList<WeatherDayHours>();
            for (Object o1 : wd.getHours()) {
                WeatherDayHours wdh = JSONObject.parseObject(((JSONObject) o1).toJSONString(), WeatherDayHours.class);
                weatherDayHoursList.add(wdh);
            }

            List<WeatherDayIndex> weatherDayIndexList = new ArrayList<WeatherDayIndex>();
            for(Object o1:wd.getIndex()){
                WeatherDayIndex wdi = JSONObject.parseObject(((JSONObject) o1).toJSONString(), WeatherDayIndex.class);
                weatherDayIndexList.add(wdi);
            }
            wd.setHours(weatherDayHoursList);
            wd.setIndex(weatherDayIndexList);
            weatherDayList.add(wd);
        }
        weather.setData(weatherDayList);
    }

    public Weather getWeather(){
        return this.weather;
    }

    public static String unicode2cn(String unicode){
        List<String> list = new ArrayList<String>();
        String reg= "\\\\u[0-9,a-f,A-F]{4}";
        Pattern p = Pattern.compile(reg);
        Matcher m=p.matcher(unicode);
        while (m.find()){
            list.add(m.group());
        }
        for (int i = 0, j = 2; i < list.size(); i++) {
            String code = list.get(i).substring(j, j + 4);
            char ch = (char) Integer.parseInt(code, 16);
            unicode = unicode.replace(list.get(i),String.valueOf(ch));
        }
        return unicode;
    }
}
