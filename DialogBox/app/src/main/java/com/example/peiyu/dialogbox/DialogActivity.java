package com.example.peiyu.dialogbox;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        Button btn = (Button) this.findViewById(R.id.buttonClick);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(DialogActivity.this);
                //LayoutInflater inflater = getLayoutInflater();
                builder.setTitle("登录");

                View v = LayoutInflater.from(DialogActivity.this).inflate(R.layout.dialog_layout, null);
                builder.setView(v);

                final EditText username = (EditText)v.findViewById(R.id.username);
                final EditText password = (EditText)v.findViewById(R.id.password);

                //add action button
                builder.setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //login
                        String str1, str2;

                        str1 = username.getText().toString();
                        str2 = password.getText().toString();
                        if(str1.equals("abc") && str2.equals("123"))
                            Toast.makeText(DialogActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(DialogActivity.this, "输入的用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //cancel
                    }
                });
                builder.show();
            }
        });
    }
}
