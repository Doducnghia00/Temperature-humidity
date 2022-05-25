package com.example.tandhv2.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private final SharedPreferences prefs;

    public Session(Context context) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUsername(String username) {
        prefs.edit().putString("username", username).apply();
    }

    public String getUsername() {
        return prefs.getString("username","");
    }

    public void setUrl(String url){
        prefs.edit().putString("url", url).apply();
    }

    public String getUrl(){
        return prefs.getString("url","");
    }

    public void setMethod(String method){
        prefs.edit().putString("method", method).apply();
    }

    public String getMethod(){
        return prefs.getString("method","");
    }


    //v2
    public String getDefaultUrlGet(){
        return "https://data.mongodb-api.com/app/application-0-ahqfu/endpoint/getInfo";
    }

    public String getDefaultUrlDelete(){
        return "https://data.mongodb-api.com/app/application-0-ahqfu/endpoint/delete?id=";
    }
    public void setUrlGet(String url){
        prefs.edit().putString("urlGet", url).apply();
    }

    public String getUrlGet(){
        return prefs.getString("urlGet","");
    }

    public void setUrlDelete(String url){
        prefs.edit().putString("urlDelete", url).apply();
    }

    public String getUrlDelete(){
        return prefs.getString("urlDelete","");
    }

    public void removeUrl(){
        prefs.edit().remove("urlGet").apply();
        prefs.edit().remove("urlDelete").apply();
    }
}
