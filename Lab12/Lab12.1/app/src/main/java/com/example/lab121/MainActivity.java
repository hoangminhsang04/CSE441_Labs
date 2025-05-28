package com.example.lab121;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1 = findViewById(R.id.selection);

        // 1. Dữ liệu nguồn
        final String arr1[] = {"Iphone 7", "SamSung Galaxy S7",
                "Nokia Lumia 730", "Sony Xperia XZ", "HTC One E9"};

        // 2. Adapter
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                arr1
        );

        // 3. Gắn adapter vào ListView
        ListView lv1 = findViewById(R.id.lv1);
        lv1.setAdapter(adapter1);

        // 4. Bắt sự kiện click
        lv1.setOnItemClickListener((parent, view, position, id) -> {
            txt1.setText("Vị trí " + position + " : " + arr1[position]);
        });
    }
}
