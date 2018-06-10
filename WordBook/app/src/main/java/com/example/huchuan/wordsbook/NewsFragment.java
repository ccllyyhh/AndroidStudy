package com.example.huchuan.wordsbook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.shuyu.action.web.ActionSelectListener;
import com.shuyu.action.web.CustomActionWebView;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment {


    View mLadingView;
    CustomActionWebView mCustomActionWebView;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        mLadingView = view.findViewById(R.id.loadingView);
        mCustomActionWebView = (CustomActionWebView)view.findViewById(R.id.customActionWebView);

        //长按搜索
        List<String> list = new ArrayList<>();
        list.add("search");

        mCustomActionWebView.setWebViewClient(new CustomWebViewClient());

        //设置item
        mCustomActionWebView.setActionList(list);

        //链接js注入接口，使能选中返回数据
        mCustomActionWebView.linkJSInterface();

        mCustomActionWebView.getSettings().setBuiltInZoomControls(true);
        mCustomActionWebView.getSettings().setDisplayZoomControls(false);
        //使用javascript
        mCustomActionWebView.getSettings().setJavaScriptEnabled(true);
        mCustomActionWebView.getSettings().setDomStorageEnabled(true);


        //增加点击回调
        mCustomActionWebView.setActionSelectListener(new ActionSelectListener() {
            @Override
            public void onClick(String title, String selectText) {
                Toast.makeText(getActivity(), "Click Item: " + title + "。\n\nValue: " + selectText, Toast.LENGTH_LONG).show();
                ((MainActivity)getActivity()).fragmentChange5();
                String selectWord=selectText;
                for (int i=0;i<selectText.length();i++){
                    char c=selectText.charAt(i);
                    if(c==' '){
                        selectWord=selectText.substring(0,i);
                        Log.i("select", "onClick: "+selectWord);
                        break;
                    }
                }
                ((MainActivity)getActivity()).search(selectWord);
            }
        });

        //加载url
        mCustomActionWebView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCustomActionWebView.loadUrl("http://wap.chinadaily.com.cn/2017-05/24/content_29467208.htm");
            }
        }, 1000);


        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mCustomActionWebView != null) {
            mCustomActionWebView.dismissAction();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private class CustomWebViewClient extends WebViewClient {

        private boolean mLastLoadFailed = false;

        @Override
        public void onPageFinished(WebView webView, String url) {
            super.onPageFinished(webView, url);
            if (!mLastLoadFailed) {
                CustomActionWebView customActionWebView = (CustomActionWebView) webView;
                customActionWebView.linkJSInterface();
                mLadingView.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageStarted(WebView webView, String url, Bitmap favicon) {
            super.onPageStarted(webView, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            mLastLoadFailed = true;
            mLadingView.setVisibility(View.GONE);
        }
    }

}
