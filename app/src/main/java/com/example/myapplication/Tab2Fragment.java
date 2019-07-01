package com.example.myapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import static java.sql.DriverManager.println;

public class Tab2Fragment extends Fragment {
    private RecyclerViewAdapter adapter;
//    public Data data;

    //for recyclerView

    View view;
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    //for ArrayList
    TextView personName, personNumber;
    ImageView photo;
    String name, number;
    Context AppContext;
    List<String> nameList = new ArrayList<>();
    List<String> numberList = new ArrayList<>();

    private ArrayList<Data> dataList = new ArrayList<>();
    private List<String> Phone_number = new ArrayList<>();
    private List<String> Phone_name = new ArrayList<>();

    public Tab2Fragment() {

    }


//    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

//        init();

//        getData();
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
////////////////////////Based on 지수&동하/////////////////////////////////////////////////
//        view = inflater.inflate(R.layout.fragment_tab2, container, false);

//        personName = (TextView) view.findViewById(R.id.name);
//        personNumber = (TextView) view.findViewById(R.id.number);
//        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        if (Phone_name.size() > 0) personName.setText("Name: " + Phone_name.get(0));
//
//
//        linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        adapter = new RecyclerViewAdapter();
//        recyclerView.setAdapter(adapter);
//
//        getContactList();
//        Log.e("NAME", Phone_name.toString());
//        getData();
        ////////////////////////////////////////////////////

        view = inflater.inflate(R.layout.fragment_tab2, container, false);

        personName = (TextView) view.findViewById(R.id.name);
        personNumber = (TextView) view.findViewById(R.id.number);
        photo = (ImageView) view.findViewById(R.id.imageView);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        ArrayList<Data> dataList = getContactList();

        for (int i = 0; i < dataList.size(); i++) {
            adapter.addItem(dataList.get(i));
        }
        adapter.notifyDataSetChanged();




        return view;
/////////////////////////////////////////////////////////////////////////////////
    }

    ///////////////////////////Based on 지수&동하////////////////////////////////
//    public void getContactList() {


//        ContentResolver cr = getActivity().getContentResolver();
//
//        Cursor c = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.RawContacts.ACCOUNT_TYPE},
//                ContactsContract.RawContacts.ACCOUNT_TYPE + " <> 'google' ", null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");
//
//        if (c.getCount() <= 0) {
//            Toast.makeText(getActivity(), "No Phone Contact Found..!", Toast.LENGTH_SHORT).show();
//        } else {
//            while (c.moveToNext()) {
//                Phone_number.add(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))); //Phone number
//                Phone_name.add(c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));  //Name of contact
//            }
//        }
//    }
    //////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Data> getContactList(){

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.Contacts._ID
        };
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                + " COLLATE LOCALIZED ASC";
        Cursor cursor = view.getContext().getContentResolver().query(uri, projection, null, selectionArgs, sortOrder);
        LinkedHashSet<Data> hashList = new LinkedHashSet<>();
        if (cursor.moveToFirst()) {
            do {
                long photo_id = cursor.getLong(2);
                long person_id = cursor.getLong(3);
                Data data = new Data();
                data.setUser_phNumber(cursor.getString(0));
                data.setUser_Name(cursor.getString(1));
                data.setPhoto_id(photo_id);
                data.setPerson_id(person_id);

                data.setPhoto(loadContactPhoto(getActivity().getContentResolver(), person_id, photo_id));

                hashList.add(data);
            } while (cursor.moveToNext());
        }
        dataList = new ArrayList<>(hashList);
        for (int i=0; i < dataList.size(); i++) {
            dataList.get(i).setId(i);
        }

        if (cursor !=  null) {
            cursor.close();
        }

        return dataList;
    }

    public Bitmap loadContactPhoto(ContentResolver cr, long id, long photo_id) {
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
        if (input != null)
            return resizingBitmap(BitmapFactory.decodeStream(input));
        else
            Log.d("PHOTO", "first try failed to load photo");

        byte[] photoBytes = null;
        Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, photo_id);
        Cursor c = cr.query(photoUri, new String[]{ContactsContract.CommonDataKinds.Photo.PHOTO}, null, null, null);
        try {
            if (c.moveToFirst())
                photoBytes = c.getBlob(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
        }

        if (photoBytes != null)
            return resizingBitmap(BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length));
        else
            Log.d("PHOTO", "second try also failed");
        return null;
    }

    public Bitmap resizingBitmap(Bitmap oBitmap) {
        if (oBitmap == null)
            return null;
        float width = oBitmap.getWidth();
        float height = oBitmap.getHeight();
        float resizing_size = 120;
        Bitmap rBitmap = null;
        if (width > resizing_size) {
            float mWidth = (float) (width/100);
            float fScale = (float) (resizing_size/mWidth);
            width *= (fScale/100);
            height *= (fScale/100);
        } else if (height > resizing_size) {
            float mHeight = (float) (height/100);
            float fScale = (float) (resizing_size / mHeight);
            width *= (fScale/100);
            height *= (fScale/100);
        }

        Log.d("rBitmap : " + width + ", " + height, "photo size");
        rBitmap = Bitmap.createScaledBitmap(oBitmap, (int) width, (int) height, true);
        return rBitmap;

    }
//    private void init() {
//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        adapter = new RecyclerViewAdapter();
//        recyclerView.setAdapter(adapter);
//    }

    ///////////////////////////Based on 지수&동하////////////////////////////////
//    private void getData() {
//        for (int i = 0; i < Phone_name.size(); i++) {
//            Data data = new Data();
//            data.setTitle(Phone_name.get(i));
//            data.setContent(Phone_number.get(i));
//            adapter.addItem(data);
//        }
//        adapter.notifyDataSetChanged();
//   }

    //////////////////////////////////////////////////////////////////////////////////////

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


}

//package com.example.myapplication;
//
//import android.content.Context;
//import android.net.Uri;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import org.json.JSONArray;
//
//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link Tab2Fragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link Tab2Fragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class Tab2Fragment extends Fragment {
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
//
////        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
////        recyclerView.setAdapter(new RecyclerViewAdapter());
//
//        return view;
//    }
//
//}
