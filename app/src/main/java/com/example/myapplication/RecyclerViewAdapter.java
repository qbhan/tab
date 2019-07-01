package com.example.myapplication;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.InputStream;
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
    public void onBindViewHolder(Holder viewHolder, final int position) {

        viewHolder.onBind(list.get(position));

        viewHolder.callDial.setOnClickListener(new View.OnClickListener() {
//            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Call on Dial", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + list.get(position).getUser_phNumber()));
                view.getContext().startActivity(i);
            }
        });
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
        private Button callDial;
//        private Context context;

        Holder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            image = itemView.findViewById(R.id.imageView);
            callDial = itemView.findViewById(R.id.callDial);
        }

        void onBind(Data data) {
            name.setText(data.getUser_Name());
            number.setText(data.getUser_phNumber());
            image.setImageBitmap(data.getPhoto());
        }
    }

//    public Bitmap loadContactPhoto(ContentResolver cr, long id, long photo_id) {
//        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
//        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
//        if (input != null)
//            return resizingBitmap(BitmapFactory.decodeStream(input));
//        else
//            Log.d("PHOTO", "first try failed to load photo");
//
//        byte[] photoBytes = null;
//        Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, photo_id);
//        Cursor c = cr.query(photoUri, new String[]{ContactsContract.CommonDataKinds.Photo.PHOTO}, null, null, null);
//        try {
//            if (c.moveToFirst())
//                photoBytes = c.getBlob(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            c.close();
//        }
//
//        if (photoBytes != null)
//            return resizingBitmap(BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length));
//        else
//            Log.d("PHOTO", "second try also failed");
//        return null;
//    }
//
//    public Bitmap resizingBitmap(Bitmap oBitmap) {
//        if (oBitmap == null)
//            return null;
//        float width = oBitmap.getWidth();
//        float height = oBitmap.getHeight();
//        float resizing_size = 120;
//        Bitmap rBitmap = null;
//        if (width > resizing_size) {
//            float mWidth = (float) (width/100);
//            float fScale = (float) (resizing_size/mWidth);
//            width *= (fScale/100);
//            height *= (fScale/100);
//        } else if (height > resizing_size) {
//            float mHeight = (float) (height/100);
//            float fScale = (float) (resizing_size / mHeight);
//            width *= (fScale/100);
//            height *= (fScale/100);
//        }
//
//        Log.d("rBitmap : " + width + ", " + height, "photo size");
//        rBitmap = Bitmap.createScaledBitmap(oBitmap, (int) width, (int) height, true);
//        return rBitmap;
//
//    }

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
