package com.example.huchuan.wordsbook;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.huchuan.wordsbook.model.Words;
import com.example.huchuan.wordsbook.util.HttpCallBackListener;
import com.example.huchuan.wordsbook.util.HttpUtil;
import com.example.huchuan.wordsbook.util.ParseJSON;
import com.example.huchuan.wordsbook.util.WordsAction;
import com.example.huchuan.wordsbook.util.WordsHandler;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;


public class SearchFragment extends Fragment {

    private EditText input;
    private ImageButton search;
    private WordsAction wordsAction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        input=(EditText)view.findViewById(R.id.input);
        search=(ImageButton)view.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputWords=input.getText().toString();
                inputWords=inputWords.replace(" ", "");
                if(inputWords!=null&&!inputWords.equals("")){
                    ((MainActivity)SearchFragment.this.getActivity()).fragmentChange();
                    ((MainActivity)SearchFragment.this.getActivity()).search(inputWords);
                }

            }
        });

        return view;
    }




}
