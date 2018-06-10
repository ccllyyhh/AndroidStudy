package com.example.huchuan.wordsbook.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.huchuan.wordsbook.db.WordsSQLiteOpenHelper;
import com.example.huchuan.wordsbook.model.Words;

import java.util.HashSet;
import java.util.Set;



public class WordsAction {
    /**
     * 本类的实例
     */
    private static WordsAction wordsAction;
    /**
     * 单词本列表名
     * */
    private final String SP_WORDS="data";
    private final String SP_KEY="Words";
    /**
     * Words的表名
     */
    private final String TABLE_WORDS = "Words";
    /**
     * 数据库工具，用于增、删、该、查
     */
    private SQLiteDatabase db;
    private MediaPlayer player = null;

    /**
     * 私有化的构造器
     */
    private WordsAction(Context context) {
        WordsSQLiteOpenHelper helper = new WordsSQLiteOpenHelper(context, TABLE_WORDS, null, 1);
        db = helper.getWritableDatabase();
    }

    /**
     * 单例类WordsAction获取实例方法
     *
     * @param context 上下文
     */
    public static WordsAction getInstance(Context context) {
        //双重效验锁，提高性能
        if (wordsAction == null) {
            synchronized (WordsAction.class) {
                if (wordsAction == null) {
                    wordsAction = new WordsAction(context);
                }
            }
        }
        return wordsAction;
    }


    /**
     * 向单词本列表添加单词
     * @param words 单词名
     * @param context 上下文
     * */
    public boolean addWordsToBook(String words,Context context){
        //判断是否是有效对象，即有数据
        if (words!=null) {
            SharedPreferences sharedPreferences=context.getSharedPreferences(SP_WORDS,Context.MODE_PRIVATE);
            Editor editor=sharedPreferences.edit();
            Set set = sharedPreferences.getStringSet(SP_KEY,new HashSet<String>());
            set.add(words);
            editor.putStringSet(SP_KEY, set);
            editor.commit();
            return true;
        }
        return false;
    }

    /**
     * 获取单词本列表
     * @param context 上下文
     * */
    public Set<String> getWordsList(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SP_WORDS,Context.MODE_PRIVATE);
        Set set = sharedPreferences.getStringSet(SP_KEY,new HashSet<String>());
        return set;
    }




    /**
     * 向数据库中保存新的Words对象
     * 会先对word进行判断，为有效值时才会保存
     *
     * @param words 单词类的实例
     */
    public boolean saveWords(Words words) {
        //判断是否是有效对象，即有数据
        if (words.getDefs().length() > 0) {
            ContentValues values = new ContentValues();
            values.put("word", words.getWord());
            values.put("BrE", words.getBrE());
            values.put("BrEmp3", words.getBrEmp3());
            values.put("AmE", words.getAmE());
            values.put("AmEmp3", words.getAmEmp3());
            values.put("defs", words.getDefs());
            values.put("sams", words.getSams());
            values.put("json",words.getJson());
            db.insert(TABLE_WORDS, null, values);
            values.clear();
            return true;
        }
        return false;
    }

    /**
     * 从数据库中查找查询的words
     *
     * @param key 查找的值
     * @return words 若返回words的key为空，则说明数据库中没有该词
     */
    public Words getWordsFromSQLite(String key) {
        Words words = new Words();
        Cursor cursor = db.query(TABLE_WORDS, null, "word=?", new String[]{key}, null, null, null);
        //数据库中有
        if (cursor.getCount() > 0) {
            Log.d("测试", "数据库中有");
            if (cursor.moveToFirst()) {
                do {
                    words.setWord(cursor.getString(cursor.getColumnIndex("word")));
                    words.setBrE(cursor.getString(cursor.getColumnIndex("BrE")));
                    words.setBrEmp3(cursor.getString(cursor.getColumnIndex("BrEmp3")));
                    words.setAmE(cursor.getString(cursor.getColumnIndex("AmE")));
                    words.setAmEmp3(cursor.getString(cursor.getColumnIndex("AmEmp3")));
                    words.setDefs(cursor.getString(cursor.getColumnIndex("defs")));
                    words.setSams(cursor.getString(cursor.getColumnIndex("sams")));
                    words.setJson(cursor.getString(cursor.getColumnIndex("json")));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            Log.d("测试", "数据库中没有");
            cursor.close();
        }

        return words;
    }


    /**
     * 从数据库中查找所有words
     */
    public Cursor getAllWordsFromSQLite() {
        return db.rawQuery("SELECT id AS _id ,word,defs FROM Words", null);
    }

    /**
     * 获取网络查找单词的对应地址
     *
     * @param key 要查询的单词
     * @return address 所查单词对应的http地址
     */
    public String getAddressForWords(final String key) {
        String address_p1 = "http://xtk.azurewebsites.net/BingDictService.aspx?Word=";
        String address_p2 = "";
        address_p2 = key;
        return address_p1 + address_p2 ;
    }
}
