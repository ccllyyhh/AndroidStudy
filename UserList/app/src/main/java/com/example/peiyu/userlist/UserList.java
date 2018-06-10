package com.example.peiyu.userlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserList extends AppCompatActivity {

    private final static String NAME = "name";
    private final static String GRADE = "grade";
    private final static String ID_NUMBER = "id_number";
    private final static String SEX = "sex";
    private final static String PHONE = "phone";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        String[] name = {"Emma", "Jessie", "Mike", "Kelly", "John"};
        String[] grade = {"1", "2", "1", "3", "1"};
        String[] id_number = {"101", "201", "102", "301", "103"};
        String[] sex = {"女", "女", "男", "女", "男"};
        String[] phone = {"7685011", "7690432", "7845743", "7839065", "7464373"};

        List<Map<String,Object>> items = new ArrayList<>();

        for(int i = 0; i < name.length; i++){
            Map<String, Object> item = new HashMap<>();
            item.put(NAME, name[i]);
            item.put(GRADE, grade[i]);
            item.put(ID_NUMBER, id_number[i]);
            item.put(SEX, sex[i]);
            item.put(PHONE, phone[i]);
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, items,
                R.layout.item,
                new String[]{NAME, GRADE, ID_NUMBER, SEX, PHONE},
                new int[]{R.id.userName, R.id.userGrade, R.id.userID, R.id.userSex, R.id.userPhone});

        ListView list = findViewById(R.id.list_item);
        list.setAdapter(adapter);
    }

}
