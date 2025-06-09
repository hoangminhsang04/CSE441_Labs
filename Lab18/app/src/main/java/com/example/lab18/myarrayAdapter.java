package com.example.lab18;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class myarrayAdapter extends ArrayAdapter<Item> {
    Activity context = null;
    ArrayList<Item> myArray = null;
    int LayoutId;

    public myarrayAdapter(Activity context, int LayoutId, ArrayList<Item> arr) {
        super(context, LayoutId, arr);
        this.context = context;
        this.LayoutId = LayoutId;
        this.myArray = arr;
    }

    static class ViewHolder {
        TextView tieude;
        TextView maso;
        ImageView btnlike;
        ImageView btnunlike;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(LayoutId, parent, false);

            holder = new ViewHolder();
            holder.tieude = convertView.findViewById(R.id.txttieude);
            holder.maso = convertView.findViewById(R.id.txtmaso);
            holder.btnlike = convertView.findViewById(R.id.btnlike);
            holder.btnunlike = convertView.findViewById(R.id.btnunlike);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Item myItem = myArray.get(position);

        holder.tieude.setText(myItem.getTieude());
        holder.maso.setText(myItem.getMaso());

        int thich = myItem.getThich();

        // Hiển thị nút like/unlike phù hợp
        if (thich == 0) {
            holder.btnlike.setVisibility(View.INVISIBLE);
            holder.btnunlike.setVisibility(View.VISIBLE);
        } else {
            holder.btnunlike.setVisibility(View.INVISIBLE);
            holder.btnlike.setVisibility(View.VISIBLE);
        }

        // Xử lý sự kiện click nút like
        holder.btnlike.setOnClickListener(v -> {
            ContentValues values = new ContentValues();
            values.put("YEUTHICH", 0);
            MainActivity.database.update("ArirangSongList", values,
                    "MABH=?", new String[]{holder.maso.getText().toString()});
            holder.btnlike.setVisibility(View.INVISIBLE);
            holder.btnunlike.setVisibility(View.VISIBLE);
            myItem.setThich(0); // Cập nhật trạng thái trong data source nếu có setter
        });

        // Xử lý sự kiện click nút unlike
        holder.btnunlike.setOnClickListener(v -> {
            ContentValues values = new ContentValues();
            values.put("YEUTHICH", 1);
            MainActivity.database.update("ArirangSongList", values,
                    "MABH=?", new String[]{holder.maso.getText().toString()});
            holder.btnunlike.setVisibility(View.INVISIBLE);
            holder.btnlike.setVisibility(View.VISIBLE);
            myItem.setThich(1); // Cập nhật trạng thái trong data source nếu có setter
        });

        // Xử lý sự kiện click vào tiêu đề bài hát
        holder.tieude.setOnClickListener(v -> {
            holder.tieude.setTextColor(Color.RED);
            holder.maso.setTextColor(Color.RED);

            Intent intent1 = new Intent(context, activitysub.class);
            Bundle bundle1 = new Bundle();
            bundle1.putString("maso", holder.maso.getText().toString());
            intent1.putExtra("package", bundle1);
            context.startActivity(intent1);
        });

        return convertView;
    }
}
