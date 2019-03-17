package com.example.tarunmethwani.bmi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import java.text.DateFormat;
import java.util.Date;

public class SubmitActivity extends AppCompatActivity {
    TextView tvBmi,tvUnderweight,tvNormal,tvOverweight,tvObese;
    String bmi,a;
    String condition,date;
    double bmI;
    SharedPreferences sp1;
    Button btnShare,btnSsave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        sp1 = getSharedPreferences("myp1", MODE_PRIVATE);

        tvBmi=(TextView)findViewById(R.id.tvBmi);
        tvUnderweight=(TextView)findViewById(R.id.tvUnderweight);
        tvNormal=(TextView)findViewById(R.id.tvNormal);
        tvOverweight=(TextView)findViewById(R.id.tvOverweight);
        tvObese=(TextView)findViewById(R.id.tvObese);
        btnShare=(Button)findViewById(R.id.btnShare);
        btnSsave=(Button)findViewById(R.id.btnSsave);
        int orientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);
        tvUnderweight.setText("You are Underweight");
        tvNormal.setText("you Are Normal");
        tvOverweight.setText("you are Overweight");
        tvObese.setText("Your are Obese");
        Date d=new Date();
        DateFormat df =DateFormat.getDateTimeInstance();
        String d1 = df.format(d);
        date=d1;


        Intent i=getIntent();
       bmi=i.getStringExtra("bmi");
        bmI=Double.parseDouble(bmi);
        double bMI = Math.round(bmI * 100.0) / 100.0;

        if(bMI<18.5) {
            condition = "Underweight";
            tvUnderweight.setTextColor(Color.RED);
        }

        else if(bMI>=18.5 && bMI<25) {
            condition = "Normal";
            tvNormal.setTextColor(Color.RED);
        }
        else if(bMI>=25 && bMI<=30) {
            condition = "Overweight";
            tvOverweight.setTextColor(Color.RED);
        }
        else {
            condition = "Obese";
            tvObese.setTextColor(Color.RED);
        }
        a= (String) bmi.subSequence(0,6);

        tvBmi.setText("Your BMI is "+a+" You are "+condition);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,"NAME= "+ sp1.getString("Name","") +"\n"+"Sex= "+sp1.getString("Gender","")
                +"\n"+"Age= "+sp1.getString("Age","")+"\n+"+"Phone= "+sp1.getString("Phone","")+"\n"+"BMI Value is "+a+"\n"+"Condition is "+condition);
                startActivity(i);


            }
        });
        btnSsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WelcomeActivity.db.addBmi(a,condition,date);
               // Toast.makeText(SubmitActivity.this, date, Toast.LENGTH_SHORT).show();

                Toast.makeText(SubmitActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();


            }
        });





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
