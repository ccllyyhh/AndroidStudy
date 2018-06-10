package com.example.huchuan.wordsbook.util;

import java.io.InputStream;

public interface HttpCallBackListener {
    public void onFinish(InputStream response);
    public void onError();
}
