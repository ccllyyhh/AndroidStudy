package com.example.huchuan.wordsbook;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huchuan.wordsbook.model.Words;
import com.example.huchuan.wordsbook.util.WordsAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class listFragment extends Fragment {

    private ListView wordslist;
    private WordsAction wordsAction;
    public listFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        //初始化控件
        wordslist=(ListView) view.findViewById(R.id.wordslist);
        Log.i("value", "wordslistWidget: "+wordslist);
        //获取数据源
        wordsAction=WordsAction.getInstance(this.getActivity());
        Set<String> wordslistData=wordsAction.getWordsList(getActivity());
        Log.i("value", "wordslist: "+wordslistData);
        final List<String> wordslistArr=new ArrayList<>();
        wordslistArr.addAll(wordslistData);
        Log.i("value", "wordslist: "+wordslistArr);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(),R.layout.wordsitem,R.id.wordsitemText,wordslistArr);
        wordslist.setAdapter(arrayAdapter);
        wordslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"点击了:"+wordslistArr.get(position),Toast.LENGTH_SHORT).show();
                Words words=wordsAction.getWordsFromSQLite(wordslistArr.get(position));
                if(words!=null){
                    ((MainActivity)getActivity()).fragmentChange3();
                    ((MainActivity)getActivity()).searchHandel(words,true);
                    ((MainActivity)getActivity()).showBack();
                }
            }
        });
        return view;
    }


}
