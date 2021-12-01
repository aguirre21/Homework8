package com.rodrigo.aguirre.homework8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphActivity extends AppCompatActivity {

    LinearLayout ll;
    ImageView mImageView;
    HashMap<String, EditText> nullHashMap;

    private static String TAG = "GraphActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        //hides the action bar
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        ll = (LinearLayout) findViewById(R.id.graphLinear);

        mImageView = new ImageView(this);

        //set layout params
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );


        //hashmap just need to call singleton Compute class
        nullHashMap = null;

        Compute test = Compute.getInstance(nullHashMap);
        mImageView.setLayoutParams(params);
        mImageView.setImageDrawable(test);
        ll.addView(mImageView);
    }
}