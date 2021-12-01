package com.rodrigo.aguirre.homework8;

import androidx.annotation.InspectableProperty;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    LinearLayout ll;
    EditText mEditText, mStudentNumEditText, mDynamicEditText;
    Button mBtn;
    HashMap<String, EditText> allEditTextHash;
    String[] arr;

    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll = (LinearLayout) findViewById(R.id.linear);
        mEditText = new EditText(this);

        //parameters for linear layout
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                //width params
                LinearLayout.LayoutParams.MATCH_PARENT,
                //height params
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        //setting params for mEditText
        mEditText.setLayoutParams(params);
        mEditText.setSingleLine();
        mEditText.setHint(R.string.letter_grade_hint);

        //adding mEditText to Linear Layout
        ll.addView(mEditText);

        //disables 1st edit text and grays it out. Then
        //runs dynamicEditText when done key is clicked
        mEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_UP) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    mEditText.setInputType(View.AUTOFILL_TYPE_NONE);
                    mEditText.setEnabled(false);
                    dynamicEditText(params);
                }
                return false;
            }
        });
    }


    //dynamically sets the number of edit text
    public void dynamicEditText(LinearLayout.LayoutParams params) {

        //editText hashmap to store mDynamicEditText
        allEditTextHash = new HashMap<>();

        //gets string from edit text
        String str = mEditText.getText().toString().toUpperCase(Locale.ROOT);

        //split strings into array
        arr = str.split(",");

        //student no. EditText
        mStudentNumEditText = new EditText(this);
        mStudentNumEditText.setLayoutParams(params);
        mStudentNumEditText.setHint(R.string.student_num_hint);
        mStudentNumEditText.setInputType(2);
        allEditTextHash.put("student no.", mStudentNumEditText);
        ll.addView(mStudentNumEditText);

        //creates EditText
        for (int i = 0; i < arr.length; i++) {
            mDynamicEditText = new EditText(this);
            mDynamicEditText.setLayoutParams(params);
            mDynamicEditText.setHint("Enter total no. of " + arr[i] + " students");
            mDynamicEditText.setSingleLine();
            mDynamicEditText.setInputType(2);
            allEditTextHash.put(arr[i], mDynamicEditText);
            ll.addView(mDynamicEditText);
        }

        //creates button to compute and display graph
        mBtn = new Button(this);
        mBtn.setLayoutParams(params);
        mBtn.setText(R.string.button_text);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sets intent to graph activity and calls
                //compute singleton to instantiate to draw
                //pie chart. Uses inputValidation to make sure
                //inputs are valid
                if (inputValidation()) {
                    Compute test = Compute.getInstance(allEditTextHash);


                    Intent intent = new Intent(MainActivity.this, GraphActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this,
                            "number of students for each letter grade does not equal total no. students",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        ll.addView(mBtn);
    }


    //checks to see that the letter grades numbers
    //add up to the student no. and that the inputs are valid
    //else it show a toast warning the user.
    public boolean inputValidation() {

        TreeMap<String, EditText> sortedValidation = new TreeMap<>(allEditTextHash);
        int[] arr = new int[allEditTextHash.size()];
        int sum = 0;
        int a = 0;

        for (String name: sortedValidation.keySet()) {
            if (sortedValidation.get(name).getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this,
                        "please enter input for letter grade " + name, Toast.LENGTH_LONG).show();

                return false;
            }
            else {
                arr[a] = Integer.parseInt(sortedValidation.get(name).getText().toString());
                a++;
            }
        }

        for (int i = 0; i <= arr.length - 2; i++) {
            sum += arr[i];
        }

        if (arr[a - 1] != sum) {

            return false;
        }

        return true;
    }
}