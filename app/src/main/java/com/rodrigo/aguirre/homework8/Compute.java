package com.rodrigo.aguirre.homework8;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

public class Compute extends Drawable {

    private static Compute computeInstance = null;

    ArrayList<String> letterGrades;
    ArrayList<Float> numberGrades;
    public TreeMap<String, EditText> sorted;

    private static String TAG = "Compute";


    //singleton constructor
    private Compute(HashMap<String, EditText> hashMap) {
        sorted = new TreeMap<>(hashMap);
    }

    //getInstance for singleton class
    public static Compute getInstance(HashMap<String, EditText> hashMap1) {
        if (computeInstance == null) {
            computeInstance = new Compute(hashMap1);
        }

        return computeInstance;
    }

    //equation to figure out the angles of the
    //pie chart size pieces
    public float[] equation(){

        letterGrades = new ArrayList<>();
        numberGrades = new ArrayList<>();

        for (String name : sorted.keySet()) {
            letterGrades.add(name);
            numberGrades.add(Float.parseFloat(sorted.get(name).getText().toString()));
        }

        float[] angle = new float[sorted.size() - 1];
        float total = numberGrades.get(numberGrades.size() - 1);

        for (int i = 0; i < numberGrades.size() - 1; i++) {
            angle[i] = (360 / total) * numberGrades.get(i);

        }

        return angle;
    }


    //used to draw the pie chart
    //and graph key
    @Override
    public void draw(@NonNull Canvas canvas) {

        float[] floatAngles =   equation();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //creates random number for paint
        Random random = new Random();


        float width = getBounds().width();
        float height = getBounds().height();
        float centerX = width/2;
        float centerY = height/2;
        float left = centerX*1/7f;
        float top = centerY*1/13f;
        float bottom = (centerY*1/13) + 77;
        float right = centerX + 77;

        Log.d(TAG, "draw: right and cx" + centerX + " right " + right + " Left " + left);

        //white rectangle background
        paint.setARGB(255, 255,255,255);
        canvas.drawRect(0,0, width , height, paint);

        //rect for pie chart
        RectF rectF = new RectF(25 , 750, width - 25, height - 250);

        //first piece of pie chart and
        //first key box
        paint.setARGB(255, 130, 201, 93);
        canvas.drawArc(rectF,0, floatAngles[0], true, paint);

        //first key box and text
        canvas.drawRect(left, top, left*2,
                top*2, paint);
        //text
        paint.setColor(Color.BLACK);
        paint.setTextSize(75);
        canvas.drawText("= " + letterGrades.get(0) + " : " + Math.round(numberGrades.get(0)),
                left*2.1f, (centerY*1/14f)*2, paint);

        //end line of keys
        canvas.drawLine(0,centerY/1.4f,width,centerY/1.4f,paint);

        Log.d(TAG, "draw: firsssssst key TOP " + top + "\n bottom " + top*2);
        //y cord where the keys should stop.
        Log.d(TAG, "draw: stop liineeee: " + centerY/1.4f );
        Log.d(TAG, "draw: ceeeeenter Y: " + centerY);


        //creates the remainder of the pie chart from
        //floatAngles starts at index 1
        //nextAngle is used to place the next piece of
        //the pie chart
        float nextAngle = floatAngles[0];
        int space = 100;
        for (int i = 1; i < floatAngles.length; i++) {
            int randNum = random.nextInt(255);
            int randNumOne = random.nextInt(255);
            int randNumTwo = random.nextInt(255);
            paint.setARGB(255, randNum, randNumOne, randNumTwo);
            canvas.drawArc(rectF, nextAngle, floatAngles[i], true, paint);
            nextAngle += floatAngles[i];


            if (top < 592f) {

                top = (((centerY*1/13) * i) + space);

                //draws key
                canvas.drawRect(left, top,
                        left*2, top + 77, paint);

                Log.d(TAG, "draw: seeecooond key - top " + top + " \n bottom " + (top + 77));
                Log.d(TAG, "draw: SPAAACe " + space);

                space *= 1.2;
            }
            else if (bottom >= 77) {
//                float bottom = (centerY*1/13) + 77;

                top = 709f;

                //draws key
                canvas.drawRect(centerX, bottom - 77,
                        right, bottom, paint);

                bottom = ((((centerY*1/13) * i) + space) + 77);

                Log.d(TAG, "draw: seeecooond key column - top " + (bottom - 77) + " \n bottom column " + bottom);
//                Log.d(TAG, "draw: SPAAACe " + space);
            }

        }
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
