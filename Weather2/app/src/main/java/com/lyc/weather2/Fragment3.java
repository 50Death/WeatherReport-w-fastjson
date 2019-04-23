package com.lyc.weather2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fragment3 extends Fragment {

    private EditText editText;

    private String jsonText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initUI();
        refreshText();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshText();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        refreshText();
    }

    public void setJsonText(String jsonText) {
        this.jsonText = jsonText;
    }

    private void initUI(){
        editText = getActivity().findViewById(R.id.editText);
    }

    private void refreshText(){
        String prettify = JSON.toJSONString(decodeUnicode(jsonText));
        editText.setText(prettify);
    }

    public String decodeUnicode(String unicode) {
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
