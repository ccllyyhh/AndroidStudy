package com.example.huchuan.wordsbook;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.huchuan.wordsbook.util.WordsAction;

import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {

    private LinearLayout wordsBook;
    private LinearLayout dailyNews;
    private WordsAction wordsAction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        //初始化控件
        wordsAction=WordsAction.getInstance(this.getActivity());
        dailyNews=(LinearLayout) view.findViewById(R.id.dailyNews);
        wordsBook=(LinearLayout) view.findViewById(R.id.myWordsBook);
        wordsBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).fragmentChange2();
            }
        });
        dailyNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).fragmentChange4();
            }
        });
        return view;
    }

}
