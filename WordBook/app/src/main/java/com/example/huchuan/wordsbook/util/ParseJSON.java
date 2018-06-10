package com.example.huchuan.wordsbook.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;



public class ParseJSON {
    public static JSONObject parse(InputStream inputStream) throws IOException, JSONException {
        //创建字节数组输出流 ，用来输出读取到的内容
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //创建读取缓存,大小为1024
        byte[] buffer = new byte[1024];
        //每次读取长度
        int len = 0;
        //开始读取输入流中的文件
        while( (len = inputStream.read(buffer) ) != -1){ //当等于-1说明没有数据可以读取了
            byteArrayOutputStream.write(buffer,0,len); // 把读取的内容写入到输出流中
        }
        //把读取到的字节数组转换为字符串
        String result = byteArrayOutputStream.toString();

        //关闭输入流和输出流
        inputStream.close();
        byteArrayOutputStream.close();
        //返回字符串结果
        JSONObject jsonObject=new JSONObject(result);
        return jsonObject;
    }
}
