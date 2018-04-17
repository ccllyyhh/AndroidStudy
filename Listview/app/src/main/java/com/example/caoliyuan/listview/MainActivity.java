package com.example.caoliyuan.listview;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final static String NAME="name";
    private final static String CLASS="class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] names={"张三","李四","王五","赵六"};
        String[] classes={"1","2","3","4"};

        List<Map<String,Object>> items=new ArrayList<Map<String,Object>>();


        for(int i=0;i<names.length;i++) {
            Map<String,Object> item=new HashMap<String,Object>();
            item.put(NAME, names[i]);
            item.put(CLASS, classes[i]);
            items.add(item);
        }

        SimpleAdapter adapter=new SimpleAdapter(this,items,R.layout.item,new String[]{NAME,CLASS},new int[]{R.id.txtProduct,R.id.txtPrice,R.id.txtConfiguration});

        ListView list=(ListView)findViewById(R.id.list);

        list.setAdapter(adapter);
    }
}
