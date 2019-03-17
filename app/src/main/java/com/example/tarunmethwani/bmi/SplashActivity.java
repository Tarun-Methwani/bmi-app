package com.example.tarunmethwani.bmi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    ImageView ivSplash;
    SharedPreferences sp1;
    Animation animation1;
    TextView tvApp,tvName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivSplash=(ImageView)findViewById(R.id.ivSplash);
        tvApp=(TextView)findViewById(R.id.tvApp);
        tvName=(TextView)findViewById(R.id.tvName);


        sp1=getSharedPreferences("myp1",MODE_PRIVATE);
        int orientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);

        animation1=AnimationUtils.loadAnimation(this,R.anim.a2);
        tvApp.startAnimation(animation1);
        tvName.startAnimation(animation1);
        ivSplash.startAnimation(animation1);


    new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();

            }
        }).start();


    }
}
