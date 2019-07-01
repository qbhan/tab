package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.constraintlayout.solver.GoalRow;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Tab1Fragment extends Fragment {
    public int imagePosition;
    public Context thiscontext;
    private File tempFile;

    private static final int PICK_FROM_CAMERA = 2;

    /** The images. */
    public static ArrayList<String> images;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_tab1,container,false);

        final GridView gallery = (GridView) view.findViewById(R.id.galleryGridView);

        gallery.setAdapter(new ImageAdapter(requireContext()));

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, final View arg1,
                                    int position, long arg3) {
                imagePosition = position;

                if (null != images && !images.isEmpty()) {

                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "원하는 동작을 선택하세요.",
                            Toast.LENGTH_SHORT).show();

                    Button btnLogin = (Button) getActivity().findViewById(R.id.edit_button);

                    btnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            arg1.setVisibility(View.GONE);
                        }
                    });

                    Button btn2Login = (Button) getActivity().findViewById(R.id.full_button);
                    btn2Login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getActivity(), FullImageActivity.class);

                            i.putExtra("path", images.get(imagePosition));
                            startActivity(i);
                        }
                    });

                }


            }

        });

        return view;
    }

    /**
     * The Class ImageAdapter.
     */
    public static class ImageAdapter extends BaseAdapter {

        /** The context. */
        public static Context context;

        /**
         * Instantiates a new image adapter.
         *
         * @param localContext
         *            the local context
         */
        public ImageAdapter(Context localContext) {
            context = localContext;
            images = getAllShownImagesPath(context);
        }

        public int getCount() {
            return images.size();
        }

        public String getImage (int position) {
            return images.get(position);
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ImageView picturesView;
            if (convertView == null) {
                picturesView = new ImageView(context);
                picturesView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                picturesView
                        .setLayoutParams(new GridView.LayoutParams(700, 700));

            } else {
                picturesView = (ImageView) convertView;
            }

            Glide.with(context).load(images.get(position))
                    //.placeholder(R.drawable.ic_launcher).centerCrop()
                    .into(picturesView);

            return picturesView;
        }

        /**
         * Getting All Images Path.
         *
         * @param activity
         *            the activity
         * @return ArrayList with images Path
         */
        private ArrayList<String> getAllShownImagesPath(Context activity) {
            Uri uri;
            Cursor cursor;
            int data,album;

            int column_index_data, column_index_folder_name;
            ArrayList<String> listOfAllImages = new ArrayList<String>();
            String absolutePathOfImage = null;
            uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            String[] projection = { MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

            cursor = activity.getContentResolver().query(uri, projection, null,
                    null, null);


            ///////<< 앨범으로 만들기
//            cursor = activity.getContentResolver().query(uri, projection, "GROUP BY (BUCKET_DISPLAY_NAME",
//                    null, null);
//            while(cursor.moveToNext()){
//
//            }
//                data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//                album = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.BUCKET_DISPLAY_NAME);
//
//                viewholder{
//                    image view = set(data);
//                    image view onclik{
//
//                    }
//                    text view = set(album);
//
//                }
//
//
//
//             cursor=   activity.getContentResolver().query(uri, projection, "BUCKET_DISTPLAY_NAME ==" +"'" +album+"'" ,
//                     null, null);
            //<<---- 2번째 view
            //////<< 앨범으로 만들기


            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            column_index_folder_name = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data);

                listOfAllImages.add(absolutePathOfImage);
            }
            return listOfAllImages;
        }
    }
}