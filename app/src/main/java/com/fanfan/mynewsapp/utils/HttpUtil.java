package com.fanfan.mynewsapp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/6/15.
 */
public class HttpUtil {
    public static String getInfo(String path) {
        String rs=null;
        BufferedReader mReader = null;
        StringBuffer sb=new StringBuffer();
        HttpURLConnection connection = null;
        try {
            URL url=new URL(path);
            connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.connect();
            InputStream is=connection.getInputStream();
            mReader=new BufferedReader(new InputStreamReader(is));
            String str;
            while ((str=mReader.readLine())!=null){
                sb.append(str).append("\n");
            }
            rs=sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                assert connection != null;
                connection.disconnect();
                mReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }
}
