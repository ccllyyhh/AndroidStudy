package com.example.huchuan.wordsbook.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by huchuan on 2017/10/17.
 */

public class Words {
    //单词
    private String word;
    //英音发音
    private String BrE;
    //英音发音的mp3地址
    private String BrEmp3;
    //美音发音
    private String AmE;
    //美音发音的mp3地址
    private String AmEmp3;
    //单词的词性与词义
    private String defs;
    //例句
    private String sams;
    //源json
    private String json;
    public Words(String word, String brE, String brEmp3, String amE, String amEmp3, String defs, String sams) {
        this.word = word;
        BrE = brE;
        BrEmp3 = brEmp3;
        AmE = amE;
        AmEmp3 = amEmp3;
        this.defs = defs;
        this.sams = sams;
    }

    public Words(){

    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getBrE() {
        return BrE;
    }

    public void setBrE(String brE) {
        BrE = brE;
    }

    public String getBrEmp3() {
        return BrEmp3;
    }

    public void setBrEmp3(String brEmp3) {
        BrEmp3 = brEmp3;
    }

    public String getAmE() {
        return AmE;
    }

    public void setAmE(String amE) {
        AmE = amE;
    }

    public String getAmEmp3() {
        return AmEmp3;
    }

    public void setAmEmp3(String amEmp3) {
        AmEmp3 = amEmp3;
    }

    public String getDefs() {
        return defs;
    }

    public void setDefs(String defs) {
        this.defs = defs;
    }

    public String getSams() {
        return sams;
    }

    public void setSams(String sams) {
        this.sams = sams;
    }

    public JSONArray getDefsList() throws JSONException {
        String json=this.getJson();
        if(json!=null){
            JSONObject jsonObject=new JSONObject(json);
            JSONArray defs=jsonObject.getJSONArray("defs");
            return defs;
        }
        return null;
    }

    public JSONArray getSamsList() throws JSONException {
        String json=this.getJson();
        if(json!=null){
            JSONObject jsonObject=new JSONObject(json);
            JSONArray sams=jsonObject.getJSONArray("sams");
            return sams;
        }
        return null;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
