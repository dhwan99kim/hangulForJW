package com.sophism.hangul;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
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
public class DrawActivity extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener{
    private String textForKorean = "가나다라마바사아자차카타파하거너더러머버서어저커처터퍼허고노도로모보소오조코토포호구누두루무부수우주추쿠투푸후그느드르므브스으즈크츠프트흐기니디리미비시이지키치티피히";
    private String textForNumber = "0123456789";
    /*private String[] texts= {"가","나","다","라","마","바","사","아","자","차","카","파","하",
                            "거","너","더","러","머","버","서","저"};*/
    boolean isNumber = false;
    private DrawingView drawView;
    private ImageButton currPaint;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNumber = getIntent().getBooleanExtra("isNumber",false);
        setContentView(R.layout.activity_draw);
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

    public void generateNextCharacter() {
        Random r = new Random();
        int num;
        String text;
        if (isNumber){
            num = r.nextInt(textForNumber.length());
            text = Character.toString(textForNumber.charAt(num));
        }else {
            num = r.nextInt(textForKorean.length());
            text = Character.toString(textForKorean.charAt(num));
        }
        TextView tv = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(400, 400);
        tv.setLayoutParams(layoutParams);
        tv.setText(text);
        tv.setTextColor(Color.YELLOW);
        tv.setTextSize(100);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/NanumGothic.ttf");
        tv.setTypeface(typeface);

        tv.setBackgroundColor(Color.TRANSPARENT);

        Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        tv.layout(0, 0, 400, 400);
        tv.draw(c);

        drawView.setBackground(getDrawableFromBitmap(bitmap));

        tts.setLanguage(Locale.KOREA);
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }

    public Drawable getDrawableFromBitmap(Bitmap bitmap){
        return new BitmapDrawable(bitmap);
    }
}
