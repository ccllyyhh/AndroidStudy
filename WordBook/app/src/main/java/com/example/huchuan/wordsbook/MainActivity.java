package com.example.huchuan.wordsbook;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.huchuan.wordsbook.model.Words;
import com.example.huchuan.wordsbook.util.HttpCallBackListener;
import com.example.huchuan.wordsbook.util.HttpUtil;
import com.example.huchuan.wordsbook.util.ParseJSON;
import com.example.huchuan.wordsbook.util.WordsAction;
import com.example.huchuan.wordsbook.util.WordsHandler;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    //fragments
    private TitleFragment titleFragment;
    private LogoFragment logoFragment;
    private SearchFragment searchFragment;
    private ContentFragment contentFragment;
    private ResultFragment resultFragmentl;
    private listFragment listFragment;
    private NewsFragment newsfragment;
    //layouts
    private LinearLayout foreground;
    private LinearLayout background;

    private ImageButton btnSearch;
    private EditText input;
    private FragmentManager fragmentManager;
    private int FragmentStatus=INIT;
    private int GroundStatus=FORE;
    private static final int INIT = 111;
    private static final int SEARCH = 122;
    private static final int CONTENT = 133;
    private static final int LIST = 144;
    private static final int FORE = 1;
    private static final int BACK = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //初始化控件
        btnSearch=(ImageButton)this.findViewById(R.id.search);
        input=(EditText)this.findViewById(R.id.input);
        fragmentManager=getFragmentManager();
        titleFragment=(TitleFragment) fragmentManager.findFragmentById(R.id.id_fragment_title);
        logoFragment=(LogoFragment) fragmentManager.findFragmentById(R.id.id_fragment_logo);
        searchFragment=(SearchFragment) fragmentManager.findFragmentById(R.id.id_fragment_search);
        contentFragment=new ContentFragment();
        resultFragmentl=new ResultFragment();
        foreground=(LinearLayout)this.findViewById(R.id.id_foreground);
        background=(LinearLayout)this.findViewById(R.id.id_background);
        if(!logoFragment.isHidden()){
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.id_foreground,contentFragment);
            transaction.commit();
        }
        FragmentStatus=this.CONTENT;

    }

    @Override
    public void onBackPressed() {
        // 这里做返回键的处理
        Log.i("back", "onBackPressed: "+this.GroundStatus);
        if(this.GroundStatus==this.BACK){
            hideBack();
        }else {
            super.onBackPressed();
        }

    }


    //布局切换至搜索结果
    public void fragmentChange(){
        if(logoFragment.isHidden()){

        }else {
            resultFragmentl=new ResultFragment();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.hide(logoFragment);
            transaction.replace(R.id.id_foreground,resultFragmentl);
            transaction.addToBackStack(null);
            transaction.commit();
            FragmentStatus=this.SEARCH;
        }

    }
    //布局切换至单词本
    public void fragmentChange2(){
        if(logoFragment.isHidden()){

        }else {
            if(listFragment==null){
                listFragment=new listFragment();
            }
            if(resultFragmentl==null){
                resultFragmentl=new ResultFragment();
            }
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.hide(logoFragment);
            transaction.hide(searchFragment);
            transaction.replace(R.id.id_foreground,listFragment);
//            transaction.replace(R.id.id_background,resultFragmentl);
            transaction.addToBackStack(null);
            transaction.commit();
            FragmentStatus=this.LIST;
        }

    }
    //显示意思
    public void fragmentChange3(){
        if(listFragment==null){
            listFragment=new listFragment();
        }
        if(resultFragmentl==null){
            resultFragmentl=new ResultFragment();
        }
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.id_background,resultFragmentl);
        transaction.commit();
        FragmentStatus=this.LIST;

    }

    //
    public void fragmentChange4(){
        if(logoFragment.isHidden()){

        }else {
            if(newsfragment==null){
                newsfragment=new NewsFragment();
            }
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.hide(logoFragment);
            transaction.hide(searchFragment);
            transaction.replace(R.id.id_foreground,newsfragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
    //
    public void fragmentChange5(){
        resultFragmentl=new ResultFragment();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.id_foreground,resultFragmentl);
        transaction.addToBackStack(null);
        transaction.commit();
        FragmentStatus=this.SEARCH;
    }

    //搜索
    public void search(String inputWords){
        WordsAction wordsAction= WordsAction.getInstance(this);
        Words wordsInSql=wordsAction.getWordsFromSQLite(inputWords);
        if(wordsInSql.getWord()!=null){
            searchHandel(wordsInSql,true);
        }
        else {
            HttpUtil.sentHttpRequest(wordsAction.getAddressForWords(inputWords), new HttpCallBackListener() {
                @Override
                public void onFinish(InputStream response) {
                    Log.i("请求状态","完成");
                    try {
                        Words words= WordsHandler.getWords(ParseJSON.parse(response));
                        searchHandel(words,false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError() {
                    Log.i("请求状态","错误");
                }
            });
        }
    }

    //搜索结果
    public void searchHandel(Words words,Boolean in){
        resultFragmentl.setContent(words,in);
    }
    //显示背景层
    public void showBack() {

        if(foreground.getVisibility()==View.VISIBLE&&background.getVisibility()==View.VISIBLE){

        }else {
            // 向右边移出
            foreground.setAnimation(AnimationUtils.makeOutAnimation(this, false));
            // 向右边移入
            background.setAnimation(AnimationUtils.makeInAnimation(this, false));
            background.setVisibility(View.VISIBLE);
            foreground.setVisibility(View.GONE);
            this.GroundStatus=this.BACK;
        }

    }
    //隐藏背景层
    public void hideBack(){
        if(foreground.getVisibility()==View.VISIBLE&&background.getVisibility()==View.VISIBLE){

        }else {
            // 向右边移出
            background.setAnimation(AnimationUtils.makeOutAnimation(this, true));
            // 向右边移入
            foreground.setAnimation(AnimationUtils.makeInAnimation(this, true));
            background.setVisibility(View.GONE);
            foreground.setVisibility(View.VISIBLE);
            this.GroundStatus=this.FORE;
        }
    }
}
