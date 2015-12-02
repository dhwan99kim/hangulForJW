package com.sophism.hangul;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;


/**
 * Created by D.H.KIM on 2015. 11. 30.
 */
public class MainActivity extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener{
    private String[] texts= {"가","나","다","라","마","바","사","아","자","차","카","파","하"};
    private DrawingView drawView;
    private ImageButton currPaint;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawView = (DrawingView)findViewById(R.id.drawing);
        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
        currPaint = (ImageButton)paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        ImageView next_btn = (ImageView) findViewById(R.id.next_btn);
        next_btn.setOnClickListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        try{
            if (tts != null){
                tts.stop();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        tts  = new TextToSpeech(getApplicationContext(),this);
        generateNextCharacter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            if (tts != null){
                tts.shutdown();
                tts = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onInit(int status) {
    }

    @Override
    public void onClick(View view){
        if(view.getId()==R.id.next_btn){
            drawView.startNew();
            generateNextCharacter();
        }
    }
    public void paintClicked(View view){
        //use chosen color
        if(view!=currPaint){
            //update color
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();
            drawView.setColor(color);

            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint=(ImageButton) view;
        }
    }

    public void generateNextCharacter(){
        Random r = new Random();
        int num = r.nextInt(texts.length-1);
        TextView tv = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(600, 400);
        tv.setLayoutParams(layoutParams);
        tv.setText(texts[num]);
        tv.setTextColor(Color.YELLOW);
        tv.setTextSize(100);
        tv.setBackgroundColor(Color.TRANSPARENT);

        Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        tv.layout(0, 0, 400, 400);
        tv.draw(c);

        drawView.setBackground(getDrawableFromBitmap(bitmap));

        tts.setLanguage(Locale.KOREA);
        tts.speak(texts[num],TextToSpeech.QUEUE_FLUSH,null);
    }

    public Drawable getDrawableFromBitmap(Bitmap bitmap){
        return new BitmapDrawable(bitmap);
    }
}
