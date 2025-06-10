package com.example.lab23;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<List> {

    private Activity context;
    private ArrayList<List> arr;
    private int layoutID;

    public MyArrayAdapter(Activity context, int layoutID, ArrayList<List> arr) {
        super(context, layoutID, arr);
        this.context = context;
        this.layoutID = layoutID;
        this.arr = arr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate layout if needed
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(layoutID, null);
        }

        final List currentItem = arr.get(position);

        // Gán hình ảnh
        ImageView imgItem = convertView.findViewById(R.id.imgview);
        if (currentItem.getImg() != null) {
            imgItem.setImageBitmap(currentItem.getImg());
        }

        // Gán tiêu đề
        TextView txtTitle = convertView.findViewById(R.id.txttitle);
        if (currentItem.getTitle() != null) {
            txtTitle.setText(currentItem.getTitle());
        }

        // Gán thông tin chi tiết
        TextView txtInfo = convertView.findViewById(R.id.txtinfo);
        if (currentItem.getInfo() != null) {
            txtInfo.setText(currentItem.getInfo());
        }

        // Xử lý sự kiện click
        if (MainActivity.lvi != null) {

        }

        return convertView;
    }
}
