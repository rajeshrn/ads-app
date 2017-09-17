package com.myapp.sqlitehelper;

/**
 * Created by Admin on 17-09-2017 20:10.
 */

public class DSInterface {
    String _uid = null;
    String _url = null;
    String _start_time = null;
    String _end_time = null;
    String _delay = null;

    public DSInterface() {
    }

    public DSInterface(String uid, String url, String start_time, String end_time, String delay) {
        this._uid = uid;
        this._url = url;
        this._start_time = start_time;
        this._end_time = end_time;
        this._delay = delay;
    }

    public String getUID() {
        return this._uid;
    }

    public String getURL() {
        return this._url;
    }

    public String getStartTime() {
        return this._start_time;
    }

    public String getEndTime() {
        return this._end_time;
    }

    public String getDelay() {
        return this._delay;
    }


    public void setUID(String uid) {
        this._uid = uid;
    }

    public void setURL(String url) {
        this._url = url ;
    }

    public void setStartTime(String start_time) {
          this._start_time = start_time;
    }

    public void setEndTime(String end_time) {
          this._end_time = end_time;
    }

    public void setDelay(String delay) {
          this._delay = delay;
    }
}
