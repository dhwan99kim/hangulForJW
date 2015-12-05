package com.sophism.hangul;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by D.H.KIM on 2015. 12. 4.
 */
public class MainActivity extends Activity implements View.OnClickListener{

    Button btnDrawLetter;
    Button btnDrawNumber;
    Button btnVideoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDrawLetter = (Button) findViewById(R.id.btn_draw_letter);
        btnDrawLetter.setOnClickListener(this);

        btnDrawNumber = (Button) findViewById(R.id.btn_draw_num);
        btnDrawNumber.setOnClickListener(this);

        btnVideoList = (Button) findViewById(R.id.btn_video_list);
        btnVideoList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_draw_letter:
                intent = new Intent(MainActivity.this, DrawActivity.class);
                intent.putExtra("isNumber", false);
                startActivity(intent);
                break;
            case R.id.btn_draw_num:
                intent = new Intent(MainActivity.this, DrawActivity.class);
                intent.putExtra("isNumber", true);
                startActivity(intent);
                break;
            case R.id.btn_video_list:
                intent = new Intent(MainActivity.this, VideoListActivity.class);
                intent.putExtra("isNumber", true);
                startActivity(intent);
                break;
        }
    }
}
