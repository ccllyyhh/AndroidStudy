package com.example.caoliyuan.calculator;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    //显示元素声明
    private Button b1=null;
    private Button b2=null;
    private Button b3=null;
    private Button b4=null;
    private Button b5=null;
    private Button b6=null;
    private Button b7=null;
    private Button b8=null;
    private Button b9=null;
    private Button b0=null;
    private Button bPlus=null;
    private Button bSub=null;
    private Button bMul=null;
    private Button bDiv=null;
    private Button bLeft=null;
    private Button bRight=null;
    private Button bEqual=null;
    private Button bDot=null;
    private Button bClean=null;
    private TextView textExp=null;
    private TextView textResult=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        b3=findViewById(R.id.b3);
        b4=findViewById(R.id.b4);
        b5=findViewById(R.id.b5);
        b6=findViewById(R.id.b6);
        b7=findViewById(R.id.b7);
        b8=findViewById(R.id.b8);
        b9=findViewById(R.id.b9);
        b0=findViewById(R.id.b0);
        bPlus=findViewById(R.id.bPlus);
        bSub=findViewById(R.id.bSub);
        bMul=findViewById(R.id.bMul);
        bDiv=findViewById(R.id.bDiv);
        bLeft=findViewById(R.id.bLeft);
        bRight=findViewById(R.id.bRight);
        bEqual=findViewById(R.id.bEqual);
        bDot=findViewById(R.id.bDot);
        bClean=findViewById(R.id.bClean);

        textExp=findViewById(R.id.textExp);
        textResult=findViewById(R.id.textResult  );


        View.OnClickListener btnListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=((Button)view).getText().toString();
                addStrInTextView(text);
            }
        };


        b1.setOnClickListener(btnListener);
        b2.setOnClickListener(btnListener);
        b3.setOnClickListener(btnListener);
        b4.setOnClickListener(btnListener);
        b5.setOnClickListener(btnListener);
        b6.setOnClickListener(btnListener);
        b7.setOnClickListener(btnListener);
        b8.setOnClickListener(btnListener);
        b9.setOnClickListener(btnListener);
        b0.setOnClickListener(btnListener);
        bPlus.setOnClickListener(btnListener);
        bSub.setOnClickListener(btnListener);
        bMul.setOnClickListener(btnListener);
        bDiv.setOnClickListener(btnListener);
        bLeft.setOnClickListener(btnListener);
        bRight.setOnClickListener(btnListener);
        bDot.setOnClickListener(btnListener);

        //单击删除一个
        bClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteOneInTextView();
            }
        });
        //长按删除全部
        bClean.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                cleanTextView();
                return true;
            }
        });

        bEqual.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                calculate();
            }
        });



    }

    //表达式添加
    private void addStrInTextView(String s){
        textExp.setText(textExp.getText()+s);
    }
    //表达式删除
    private void deleteOneInTextView(){
        String originStr=textExp.getText().toString();
        //避免越界
        String newStr = originStr.length()>0 ? originStr.substring(0,textExp.getText().length()-1) : originStr;
        textExp.setText(newStr);
    }
    //清空
    private void cleanTextView(){
        textExp.setText("");
    }
    //计算
    private void calculate(){
        String exp=textExp.getText().toString();
        Calculator calculator=new Calculator();
        String rsl=calculator.calculate(exp.replace('×','*'));
        textResult.setText(rsl);
    }
}
