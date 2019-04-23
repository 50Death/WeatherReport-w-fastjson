package com.lyc.weather2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.lyc.weather2.data.Weather;
import com.lyc.weather2.data.WeatherDay;
import com.lyc.weather2.data.WeatherDayHours;
import com.lyc.weather2.data.WeatherDayIndex;
import com.lyc.weather2.data.WeatherJSON;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fragment2 extends Fragment {

    private SearchView searchView;

    private Weather weather;

    private TextView cityTextView;
    private TextView infoTextView;
    private TextView timeTextView;
    private TextView airTextView;
    private ImageView imageView;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String city;

    private ArrayAdapter<String> mArrayAdapter;
    private List<String> weatherList;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment2,container,false);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
        initComponent();
    }

    private void initUI() {
        searchView = (SearchView) getActivity().findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //View view = getActivity().findViewById(R.id.fragment3);
                city = query;
                initWeather(city);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        cityTextView = (TextView) getActivity().findViewById(R.id.textView7);
        infoTextView = (TextView) getActivity().findViewById(R.id.textView8);
        timeTextView = (TextView) getActivity().findViewById(R.id.textView9);
        airTextView = (TextView) getActivity().findViewById(R.id.textView10);
        imageView = (ImageView) getActivity().findViewById(R.id.imageView1);
        listView = (ListView) getActivity().findViewById(R.id.listView1);
    }

    private void initComponent() {
        weatherList = new ArrayList<>();
        mArrayAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, weatherList);
        listView.setAdapter(mArrayAdapter);

    }

    public void initWeather(final String city) {
        final String WEATHER_URL_LOCAL = "https://www.tianqiapi.com/api/";
        final String WEATHER_URL = "https://www.tianqiapi.com/api/?version=v1&city=";

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String text = data.getString("json");
                weather = new WeatherJSON(text).getWeather();
                refreshWeatherUI(weather);

            }
        };

        new Thread() {
            @Override
            public void run() {
                super.run();
                String json = null;
                try {
                    String url;
                    if(city==null){
                        url = WEATHER_URL_LOCAL;
                    }else {
                        url = WEATHER_URL + city;
                    }
                    //使用Jsoup获取网页JSON
                    //请求头配置来自Google Chrome
                    Connection.Response res = Jsoup.connect(url)
                            .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                            .header("Accept-Encoding", "gzip, deflate, br")
                            .header("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
                            .header("Content-Type", "application/json;charset=UNICODE")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36")
                            .timeout(3000)
                            .ignoreContentType(true)
                            .execute();
                    json = res.body();
                    Log.i("JSON", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("json", json);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        }.start();
    }

    private void refreshWeatherUI(Weather weather) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String hours = sdf.format(new Date());
        cityTextView.setText(weather.getCity());
        timeTextView.setText(weather.getUpdate_time());
        infoTextView.setText(WeatherJSON.unicode2cn(((WeatherDay) weather.getData().get(0)).getWea())
                + "  " + WeatherJSON.unicode2cn(((WeatherDay) weather.getData().get(0)).getTem()));
        airTextView.setText("空气质量: " + ((WeatherDay) weather.getData().get(0)).getAir()
                + "  " + ((WeatherDay) weather.getData().get(0)).getAir_level());

        String sky = (WeatherJSON.unicode2cn(((WeatherDay) weather.getData().get(0)).getWea()));
        if (sky.contains("阵雨")) {
            imageView.setImageResource(R.mipmap.rain_6);
        } else if (sky.contains("大雨")) {
            imageView.setImageResource(R.mipmap.rain_3);
        } else if (sky.contains("中雨")) {
            imageView.setImageResource(R.mipmap.rain_2);
        } else if (sky.contains("小雨")) {
            imageView.setImageResource(R.mipmap.rain_1);
        } else if (sky.contains("多云")) {
            if (Integer.parseInt(hours) >= 19 || Integer.parseInt(hours) <= 5) {
                imageView.setImageResource(R.mipmap.cloudy_night);
            } else {
                imageView.setImageResource(R.mipmap.cloudy_day);
            }
        } else if (sky.contains("阴")) {
            imageView.setImageResource(R.mipmap.very_cloudy);
        } else if (sky.contains("晴")) {
            if (Integer.parseInt(hours) >= 19 || Integer.parseInt(hours) <= 5) {
                imageView.setImageResource(R.mipmap.sunny_night);
            } else {
                imageView.setImageResource(R.mipmap.sunny_day);
            }
        } else {
            imageView.setImageResource(R.mipmap.unset);//TODO
        }

        weatherList.clear();
        mArrayAdapter.clear();
        for (int i = 0; i < 7; i++) {
            weatherList.add(WeatherJSON.unicode2cn(((WeatherDay) weather.getData().get(i)).getDay()));
            weatherList.add(WeatherJSON.unicode2cn(((WeatherDay) weather.getData().get(i)).getWea()));
            weatherList.add("空气质量: \n" + ((WeatherDay) weather.getData().get(i)).getAir() + "  " + ((WeatherDay) weather.getData().get(i)).getAir_level());
            weatherList.add("湿度: \n" + ((WeatherDay) weather.getData().get(i)).getWea() + "%");
            weatherList.add(WeatherJSON.unicode2cn(((WeatherDay) weather.getData().get(i)).getWea()));
            weatherList.add("最高温度: \n" + WeatherJSON.unicode2cn(((WeatherDay) weather.getData().get(i)).getTem1()));
            weatherList.add("最低温度: \n" + WeatherJSON.unicode2cn(((WeatherDay) weather.getData().get(i)).getTem2()));
            weatherList.add(WeatherJSON.unicode2cn((String) ((WeatherDay) weather.getData().get(i)).getWin().get(0)) + "\n"
                    + WeatherJSON.unicode2cn(((WeatherDay) weather.getData().get(i)).getWin_speed()));
            for (Object wdh : ((WeatherDay) weather.getData().get(i)).getHours()) {
                weatherList.add("\t" + ((WeatherDayHours) wdh).getDay());
                weatherList.add("\t" + ((WeatherDayHours) wdh).getWea());
                weatherList.add("\t" + ((WeatherDayHours) wdh).getTem());
                weatherList.add("\t" + ((WeatherDayHours) wdh).getWin());
                weatherList.add("\t" + ((WeatherDayHours) wdh).getWin_speed());
            }

            for(Object wdi:((WeatherDay) weather.getData().get(i)).getIndex()){
                weatherList.add("\t" + ((WeatherDayIndex)wdi).getTitle() + "\n\t"
                        +((WeatherDayIndex)wdi).getLevel()+"  "+((WeatherDayIndex)wdi).getDesc());
            }
        }
        mArrayAdapter.add("Powered by: xX50DeathXx.top");
    }
}
