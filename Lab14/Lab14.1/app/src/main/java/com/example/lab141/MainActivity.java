package com.example.lab141;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edta, edtb;
    Button btncong;
    ListView lv1;
    ArrayList<String> list;
    ArrayAdapter<String> myarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Thiết lập TabHost
        TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec spec1 = tabHost.newTabSpec("t1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Nhập liệu");
        tabHost.addTab(spec1);

        TabHost.TabSpec spec2 = tabHost.newTabSpec("t2");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("Kết quả");
        tabHost.addTab(spec2);

        addControl();
        addEvent();
    }

    private void addControl() {
        edta = findViewById(R.id.edta);
        edtb = findViewById(R.id.edtb);
        btncong = findViewById(R.id.btncong);
        lv1 = findViewById(R.id.lv1);

        list = new ArrayList<>();
        myarray = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lv1.setAdapter(myarray);
    }

    private void addEvent() {
        btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyCong();
            }
        });
    }

    private void xuLyCong() {
        try {
            int a = Integer.parseInt(edta.getText().toString().trim());
            int b = Integer.parseInt(edtb.getText().toString().trim());
            String result = a + " + " + b + " = " + (a + b);
            list.add(result);
            myarray.notifyDataSetChanged();
            edta.setText("");
            edtb.setText("");
        } catch (NumberFormatException e) {
            edta.setError("Vui lòng nhập số hợp lệ");
            edtb.setError("Vui lòng nhập số hợp lệ");
        }
    }
}
