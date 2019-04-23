package com.lyc.weather2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.lyc.weather2.data.WeatherJSON;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private Fragment1 fragment1;//天气
    private Fragment2 fragment2;//JSON过的数据
    private Fragment3 fragment3;//JSON源码
    private Fragment[] fragments;
    private int lastfragment;

    private String jsonText;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (lastfragment != 0) {
                        switchFragment(lastfragment, 0);
                        lastfragment = 0;
                    }
                    return true;
                case R.id.navigation_dashboard:
                    if (lastfragment != 1) {
                        switchFragment(lastfragment, 1);
                        lastfragment = 1;
                    }
                    return true;
                case R.id.navigation_notifications:
                    if (lastfragment != 2) {
                        switchFragment(lastfragment, 2);
                        lastfragment = 2;
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initUI();
        initFragment();
        //initLocalWeather();
    }

    private void initUI() {
        bottomNavigationView = findViewById(R.id.navigation);
    }

    private void initFragment() {
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragments = new Fragment[]{fragment1, fragment2, fragment3};
        lastfragment = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).show(fragment1).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void switchFragment(int lastfragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.container, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

    /**
     * 根据IP地址位置获取天气信息的JSON
     * MainActivity不允许进行Http访问，所以使用Handler+多线程解决获取值问题
     * 并传值给Fragment1/3用于显示
     * TODO:传值给Fragment2用于fastjson
     */
    private void initLocalWeather() {
        final String WEATHER_URL_LOCAL = "https://www.tianqiapi.com/api/";
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String text = data.getString("json");
                fragment3.setJsonText(text);
                fragment1.setWeather(new WeatherJSON(text).getWeather());
                jsonText = text;
            }
        };

        new Thread() {
            @Override
            public void run() {
                super.run();
                String json = null;
                try {
                    //使用Jsoup获取网页JSON
                    //请求头配置来自Google Chrome
                    Connection.Response res = Jsoup.connect(WEATHER_URL_LOCAL)
                            .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                            .header("Accept-Encoding","gzip, deflate, br")
                            .header("Accept-Language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
                            .header("Content-Type","application/json;charset=UNICODE")
                            .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36")
                            .timeout(3000)
                            .ignoreContentType(true)
                            .execute();
                    json = res.body();
                    Log.i("JSON",json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("json",json);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        }.start();

    }

}
