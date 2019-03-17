package com.example.tarunmethwani.bmi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;



public class HistoryActivity extends AppCompatActivity {
    TextView tvHistory;
    SharedPreferences sp1;
    StringBuffer sb1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        tvHistory=(TextView)findViewById(R.id.tvData);
        int orientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);
        String data=WelcomeActivity.db.viewBmi();
        if(data.length()==0) {
            tvHistory.setText("no records to show");
            return;
        }

        else
            tvHistory.setText(data);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1,menu);
        return  true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.website)
        {
            Intent i =new Intent((Intent.ACTION_VIEW));
            i.setData(Uri.parse("http://"+"www.Instagram.com"));
            startActivity(i);
        }
        if(item.getItemId()==R.id.about)
        {
            Toast.makeText(this, "APP Developed by Mr.Tarun Methwani", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }

}

