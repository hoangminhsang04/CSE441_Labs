package com.example.lab21;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnparse;
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnparse = findViewById(R.id.btnparse);
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(myadapter);

        btnparse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parsejson();
            }

            private void parsejson() {
                String json = null;
                try {
                    InputStream inputStream = getAssets().open("computer.json");
                    int size = inputStream.available();
                    byte[] buffer = new byte[size];
                    inputStream.read(buffer);
                    inputStream.close();
                    json = new String(buffer, "UTF-8");
                    JSONObject reader = new JSONObject(json);

                    // Thêm MaDM và TenDM
                    mylist.add("MaDM: " + reader.getString("MaKH"));
                    mylist.add("TenDM: " + reader.getString("TenKH"));

                    // Lấy mảng Sanphams
                    JSONArray myarray = reader.getJSONArray("Sanphams");
                    for (int i = 0; i < myarray.length(); i++) {
                        JSONObject myobj = myarray.getJSONObject(i);
                        mylist.add("MaSP: " + myobj.getString("MaSP") + " - TenSP: " + myobj.getString("TenSP"));
                        mylist.add("SoLuong: " + myobj.getString("SoLuong") + " * DonGia: " + myobj.getString("DonGia") + " = ThanhTien: " + myobj.getString("ThanhTien"));
                        mylist.add("Hinh: " + myobj.getString("Hinh"));
                    }

                    myadapter.notifyDataSetChanged();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        });
    }
}
