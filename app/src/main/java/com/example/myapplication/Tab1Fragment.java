package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class Tab1Fragment extends Fragment {
    public Context thiscontext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thiscontext = container.getContext();

        View view = inflater.inflate(R.layout.fragment_tab1,container,false);

        GridView gridView = (GridView)view.findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(thiscontext));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long ld) {
                Intent i = new Intent(getActivity(),FullImageActivity.class );
                //Intent i = new Intent(getApplicationContext(),FullImageActivity.class );
                i.putExtra("id",position);
                startActivity(i);
            }
        });

        return view;

    }
}
