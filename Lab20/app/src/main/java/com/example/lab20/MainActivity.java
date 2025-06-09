package com.example.lab20;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnStart;
    //khai báo MyAsyncTask
    MyAsyncTask mytt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart=(Button) findViewById(R.id.button1);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
// TODO Auto-generated method stub
                doStart();
            }
        });
    }
    private void doStart()
    {
//truyền this (chính là MainActivity hiện tại) qua backgroud Thread
        mytt =new MyAsyncTask(this);
//Kích hoạt Tiến trình
//khi gọi hàm này thì onPreExecute của mytt sẽ thực thi trước
        mytt.execute();
    }
}