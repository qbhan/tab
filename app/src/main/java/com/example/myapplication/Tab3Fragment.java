package com.example.myapplication;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab3Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab3Fragment extends Fragment {

    ImageView iv_card_left, iv_card_right;
    TextView tv_score_left, tv_score_right;
    Button b_deal, b_reset;

    Random r, rr;

    int leftScore, rightScore = 0;
    String[] shapes = {"clubs", "diamonds", "hearts", "spades"};


    MediaPlayer mp = new MediaPlayer();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

//        Button Play = (Button)

        iv_card_left = (ImageView) view.findViewById(R.id.iv_card_left);
        iv_card_right = (ImageView) view.findViewById(R.id.iv_card_right);
        tv_score_left = (TextView) view.findViewById(R.id.tv_score_left);
        tv_score_right = (TextView) view.findViewById(R.id.tv_score_right);
        b_deal = (Button) view.findViewById(R.id.b_deal);
        b_reset = (Button) view.findViewById(R.id.b_reset);

//        b_deal.setOnClickListener();

        r = new Random();
        rr = new Random();



        b_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //generate the two card numbers
                int leftCard = r.nextInt(13) + 2; //this is for cards 2 - 14
                int rightCard = r.nextInt(13) + 2; //this is for cards 2 - 14
                String leftShape = shapes[rr.nextInt(3)];
                String rightShape = shapes[rr.nextInt(3)];

                //show card images
                int leftImage = getResources().getIdentifier(leftShape + leftCard, "drawable", getActivity().getPackageName());
                iv_card_left.setImageResource(leftImage);
                int rightImage = getResources().getIdentifier(rightShape + rightCard, "drawable", getActivity().getPackageName());
                iv_card_right.setImageResource(rightImage);

                //compare cards, add points and display them
                if (leftCard > rightCard) {
                    leftScore++;
                    tv_score_left.setText(String.valueOf(leftScore));
                } else if(leftCard < rightCard) {
                    rightScore++;
                    tv_score_right.setText(String.valueOf(rightScore));
                } else {
                    if (leftScore > rightScore) {
                        Toast.makeText(requireContext(), "LEFT SIDE WIN!!", Toast.LENGTH_LONG).show();
                    } else if (leftScore < rightScore) {
                        Toast.makeText(requireContext(), "RIGHT SIDE WIN!!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(requireContext(), "TIED UP!!!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        b_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialize scores
                leftScore = 0;
                tv_score_left.setText(String.valueOf(leftScore));
                rightScore = 0;
                tv_score_right.setText(String.valueOf(rightScore));

                //initialize cards
                int leftImage = getResources().getIdentifier("black_joker", "drawable", getActivity().getPackageName());
                iv_card_left.setImageResource(leftImage);
                int rightImage = getResources().getIdentifier("black_joker", "drawable", getActivity().getPackageName());
                iv_card_right.setImageResource(rightImage);

            }
        });
        return view;

//        return inflater.inflate(R.layout.fragment_tab3, container, false);
    }
}

