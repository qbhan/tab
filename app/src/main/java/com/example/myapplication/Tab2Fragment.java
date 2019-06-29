package com.example.myapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import static java.sql.DriverManager.println;

public class Tab2Fragment extends Fragment {
    private RecyclerViewAdapter adapter;

    //for recyclerView

    View view;
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    //for ArrayList
    TextView personName, personNumber;
    String name, number;
    Context AppContext;
    List<String> nameList = new ArrayList<>();
    List<String> numberList = new ArrayList<>();

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


//    public ArrayList<Data> onRequestPermission(int requestCode, String[]permissions, int[] grantResults) {
//        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                return getContactList();
//            } else {
//                return null;
//            }
//        } else {
//            return null;
//        }
//    }
//
//    private void addContacts() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, );
//        }
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tab2, container, false);
//        view.setContentView(R.layout.fragment_tab2);

        personName = (TextView) view.findViewById(R.id.name);
        personNumber = (TextView) view.findViewById(R.id.number);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        if (Phone_name.size() > 0) personName.setText("Name: " + Phone_name.get(0));


        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        getContactList();
        Log.e("NAME", Phone_name.toString());
        getData();

//        dataList = getContactList();
////        dataList = onRequestPermission()
//        adapter.addItems(dataList);


        // 여기는 사진 넣는 튜토리얼얼
//       List<String> listTitle = Arrays.asList("아보카도1", "아보카도2", "아보카도3", "아보카도4",
//                "아보카도5", "아보카도6", "아보카도7", "아보카도8", "아보카도9", "아보카도10",
//                "아보카도11", "아보카도12", "아보카도13", "아보카도14", "아보카도15", "아보카도16");
//        List<String> listContent = Arrays.asList(
//                "이 꽃은 국화입니다.",
//                "여기는 사막입니다.",
//                "이 꽃은 수국입니다.",
//                "이 동물은 해파리입니다.",
//                "이 동물은 코알라입니다.",
//                "이것은 등대입니다.",
//                "이 동물은 펭귄입니다.",
//                "이 꽃은 튤립입니다.",
//                "이 꽃은 국화입니다.",
//                "여기는 사막입니다.",
//                "이 꽃은 수국입니다.",
//                "이 동물은 해파리입니다.",
//                "이 동물은 코알라입니다.",
//                "이것은 등대입니다.",
//                "이 동물은 펭귄입니다.",
//                "이 꽃은 튤립입니다."
//        );
//        List<Integer> listResId = Arrays.asList(
//                R.drawable.pic1,
//                R.drawable.pic1,
//                R.drawable.pic1,
//                R.drawable.pic1,
//                R.drawable.pic1,
//                R.drawable.pic1,
//                R.drawable.pic1,
//                R.drawable.pic1,
//                R.drawable.pic1,
//                R.drawable.pic1,
//                R.drawable.pic1,
//                R.drawable.pic1,
//                R.drawable.pic1,
//                R.drawable.pic1,
//                R.drawable.pic1,
//                R.drawable.pic1
//        );
//        for (int i = 0; i < listTitle.size(); i++) {
//            // 각 List의 값들을 data 객체에 set 해줍니다.
//            Data data = new Data();
//            data.setTitle(listTitle.get(i));
//            data.setContent(listContent.get(i));
//            data.setResId(listResId.get(i));
//
//            // 각 값이 들어간 data를 adapter에 추가합니다.
//            adapter.addItem(data);
//        }
//
//
//
//
//        // adapter의 값이 변경되었다는 것을 알려줍니다.
//        adapter.notifyDataSetChanged();


        return view;

    }


    public void getContactList() {

        ContentResolver cr = getActivity().getContentResolver();

        Cursor c = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.RawContacts.ACCOUNT_TYPE},
                ContactsContract.RawContacts.ACCOUNT_TYPE + " <> 'google' ", null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");

        if (c.getCount() <= 0) {
            Toast.makeText(getActivity(), "No Phone Contact Found..!", Toast.LENGTH_SHORT).show();
        } else {
            while (c.moveToNext()) {
                Phone_number.add(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))); //Phone number
                Phone_name.add(c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));  //Name of contact
            }
        }

//        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
//        String[] projection = new String[] {
//                ContactsContract.CommonDataKinds.Phone.NUMBER,
//                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
//                ContactsContract.Contacts.PHOTO_ID,
//                ContactsContract.Contacts._ID
//        };
//        String[] selectionArgs = null;
//        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
//                + " COLLATE LOCALIZED ASC";
//        Cursor cursor = view.getContext().getContentResolver().query(uri, projection, null, selectionArgs, sortOrder);
//        LinkedHashSet<Data> hashList = new LinkedHashSet<>();
//        if (cursor.moveToFirst()) {
//            do {
//                Data data = new Data();
//                data.setNumber(cursor.getString(0));
//                data.setName(cursor.getString(1));
//                long image_id = cursor.getLong(2);
//                long person_id = cursor.getLong(3);
//                data.setImage_id(image_id);
//                data.setPerson_id(person_id);
//
//                hashList.add(data);
//            } while (cursor.moveToNext());
//        }
//        dataList = new ArrayList<>(hashList);
//        for (int i=0; i < dataList.size(); i++) {
//            dataList.get(i).setId(i);
//        }
//
//        if (cursor !=  null) {
//            cursor.close();
//        }
//
//        return dataList;
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

    private void getData() {
//        // 임의의 데이터입니다.
//        List<String> listTitle = Arrays.asList("아보카도1", "아보카도2", "아보카도3", "아보카도4",
//                "아보카도5", "아보카도6", "아보카도7", "아보카도8", "아보카도9", "아보카도10",
//                "아보카도11", "아보카도12", "아보카도13", "아보카도14", "아보카도15", "아보카도16");
//        List<String> listContent = Arrays.asList(
//                "이 꽃은 국화입니다.",
//                "여기는 사막입니다.",
//                "이 꽃은 수국입니다.",
//                "이 동물은 해파리입니다.",
//                "이 동물은 코알라입니다.",
//                "이것은 등대입니다.",
//                "이 동물은 펭귄입니다.",
//                "이 꽃은 튤립입니다.",
//                "이 꽃은 국화입니다.",
//                "여기는 사막입니다.",
//                "이 꽃은 수국입니다.",
//                "이 동물은 해파리입니다.",
//                "이 동물은 코알라입니다.",
//                "이것은 등대입니다.",
//                "이 동물은 펭귄입니다.",
//                "이 꽃은 튤립입니다."
//        );
//        List<Integer> listResId = Arrays.asList(
//                R.drawable.pic1,
//                R.drawable.pic2,
//                R.drawable.pic3,
//                R.drawable.pic4,
//                R.drawable.pic4,
//                R.drawable.pic5,
//                R.drawable.pic6,
//                R.drawable.pic7,
//                R.drawable.pic8,
//                R.drawable.pic9,
//                R.drawable.pic10,
//                R.drawable.pic11,
//                R.drawable.pic12,
//                R.drawable.pic13,
//                R.drawable.pic14,
//                R.drawable.pic15
//        );
//        for (int i = 0; i < listTitle.size(); i++) {
//            // 각 List의 값들을 data 객체에 set 해줍니다.
//            Data data = new Data();
//            data.setTitle(listTitle.get(i));
//            data.setContent(listContent.get(i));
//            data.setResId(listResId.get(i));
//
//            // 각 값이 들어간 data를 adapter에 추가합니다.
//            adapter.addItem(data);
//        }
//
//
//
//
//        // adapter의 값이 변경되었다는 것을 알려줍니다.
//        adapter.notifyDataSetChanged();
//    }
        for (int i = 0; i < Phone_name.size(); i++) {
            Data data = new Data();
            data.setTitle(Phone_name.get(i));
            data.setContent(Phone_number.get(i));
            adapter.addItem(data);
        }
        adapter.notifyDataSetChanged();
    }

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
