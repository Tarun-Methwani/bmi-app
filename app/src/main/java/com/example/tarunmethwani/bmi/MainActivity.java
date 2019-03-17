package com.example.tarunmethwani.bmi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;



public class MainActivity extends AppCompatActivity{
    Button btnSave;
    EditText etAge,etName,etPhone;
    RadioGroup rgGender;
    TextView tvLogin;
    String Age,Name,Phone,Gender;
    SharedPreferences sp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSave = (Button) findViewById(R.id.btnSave);
        rgGender=(RadioGroup)findViewById(R.id.rgGender);
        tvLogin=(TextView)findViewById(R.id.tvLogin);
        etAge = (EditText) findViewById(R.id.etAge);
        etName = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        sp1=getSharedPreferences("myp1",MODE_PRIVATE);
        int orientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);

        if(sp1.contains("Name"))
        {
            Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
            i.putExtra("Name", sp1.getString("Name",""));
            i.putExtra("Gender", sp1.getString("Gender",""));
            startActivity(i);
            finish();
        }

        else
        {

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Age = etAge.getText().toString();
                    Name = etName.getText().toString();
                    Phone = etPhone.getText().toString();
                    int s1=rgGender.getCheckedRadioButtonId();
                    RadioButton rb=(RadioButton)findViewById(s1);
                   Gender=rb.getText().toString().trim();


                    SharedPreferences.Editor editor = sp1.edit();
                    editor.putString("Name", Name);
                    editor.putString("Gender",Gender);
                    editor.putString("Age",Age);
                    editor.putString("Phone",Phone);
                    editor.apply();


                    if (Phone.length() != 10) {
                        etPhone.setError("Enter valid phone number..!");
                    }
                    else if(Name.length()==0)
                    {
                        etName.setError("Please Enter your Name");
                    }
                    else if(Name.contains("1")||Name.contains("2")||Name.contains("3")||Name.contains("4")||Name.contains("5")||Name.contains("6")||Name.contains("7")||Name.contains("8")||Name.contains("9")||Name.contains("0"))
                    {
                        etName.setError("Enter Appropriate Name");
                    }
                    else if(Age.length()==0)
                    {
                        etAge.setError("Enter your age");
                    }
                    else if(Age.equals("0"))
                    {
                        etAge.setError("Age cant be Zero");
                    }
                    else if(Gender.length()==0)
                    {
                        Toast.makeText(MainActivity.this, "Please select your Gender", Toast.LENGTH_SHORT).show();
                    }
                    else {


                        Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
                        i.putExtra("Name", Name);
                        i.putExtra("Gender",Gender);
                        startActivity(i);
                        Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        finish();

                    }


                }
            });



       }


        
    }
}
