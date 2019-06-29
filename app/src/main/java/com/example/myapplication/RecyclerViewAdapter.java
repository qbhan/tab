package com.example.myapplication;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Holder> {

    //Variables for Contacts
    private ArrayList<Data> list = new ArrayList<>();

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {

        viewHolder.onBind(list.get(position));
    }

    void addItem(Data data) {
        list.add(data);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    



    public class Holder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView number;
        private ImageView image;

        Holder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            image = itemView.findViewById(R.id.imageView);
        }

        void onBind(Data data) {
            name.setText(data.getTitle());
            number.setText(data.getContent());
            image.setImageResource(data.getResId());
        }
    }

    // For RecyclerView Tutorial
    // adapter에 들어갈 list 입니다.
//    private ArrayList<Data> listData = new ArrayList<>();
//
//    @NonNull
//    @Override
//    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
//        // return 인자는 ViewHolder 입니다.
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
//        return new Holder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull Holder holder, int position) {
//        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
//        holder.onBind(listData.get(position));
//    }
//
//    public int getItemCount() {
//        return listData.size();
//    }
//
//    void addItem(Data data) {
//        listData.add(data);
//    }
//
//    public class Holder extends RecyclerView.ViewHolder{
//
//        private TextView textView1;
//        private TextView textView2;
//        private ImageView imageView;
//
//        Holder(View itemView) {
//            super(itemView);
//
//            textView1 = itemView.findViewById(R.id.name);
//            textView2 = itemView.findViewById(R.id.number);
//            imageView = itemView.findViewById(R.id.imageView);
//        }
//
//        void onBind(Data data) {
//            textView1.setText(data.getName());
//            textView2.setText(data.getNumber());
//            imageView.setImageResource(data.getId());
//        }
//    }

}
