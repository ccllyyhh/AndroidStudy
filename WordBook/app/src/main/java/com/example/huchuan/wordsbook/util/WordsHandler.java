package com.example.huchuan.wordsbook.util;

import com.example.huchuan.wordsbook.model.Words;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by huchuan on 2017/10/17.
 */

public class WordsHandler {
    public static Words getWords(JSONObject jsonObject) throws JSONException {
        Words words=new Words();
        //单词
        String word=jsonObject.getString("word");
        //发音
        JSONObject pronunciation=jsonObject.getJSONObject("pronunciation");
        String AmE=pronunciation.getString("AmE");
        String AmEmp3=pronunciation.getString("AmEmp3");
        String BrE=pronunciation.getString("BrE");
        String BrEmp3=pronunciation.getString("BrEmp3");
        //意思
        String defs=jsonObject.getString("defs");
        //例句
        String sams=jsonObject.getString("sams");

        //set
        words.setWord(word);
        words.setAmE(AmE);
        words.setAmEmp3(AmEmp3);
        words.setBrE(BrE);
        words.setBrEmp3(BrEmp3);
        words.setDefs(defs);
        words.setSams(sams);
        words.setJson(jsonObject.toString());
        return words;
    }
}
