package com.example.tarunmethwani.bmi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WelcomeActivity extends AppCompatActivity  implements
        GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    TextView tvName, tvLocation, tvTemperature, tvHeight, tvHeightFeet, tvHeightInch, tvWeight;
    Spinner spnHeightFeet, spnHeightInch;
    Button btnCalculate, btnHistory;
    EditText etWeight;

    Location loc;
    double Weight;
    double bmi;
    double Height;
    int feet, inch;
    String Name, Gender,msg;
    SharedPreferences sp1;
    static MeraDbHandler db;

    static String msgL, msgT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        spnHeightFeet = (Spinner) findViewById(R.id.spnHeightFeet);
        spnHeightInch = (Spinner) findViewById(R.id.spnHeightInch);

        tvName = (TextView) findViewById(R.id.tvName);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnHistory = (Button) findViewById(R.id.btnHistory);
        etWeight = (EditText) findViewById(R.id.etWeight);
        tvName = (TextView) findViewById(R.id.tvName);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvTemperature = (TextView) findViewById(R.id.tvTemperature);
        tvHeight = (TextView) findViewById(R.id.tvHeight);
        tvHeightInch = (TextView) findViewById(R.id.tvHeightInch);
        tvHeightFeet = (TextView) findViewById(R.id.tvHeightFeet);
        tvWeight = (TextView) findViewById(R.id.tvWeight);
        sp1 = getSharedPreferences("myp1", MODE_PRIVATE);
        int orientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);
        db=new MeraDbHandler(this);

        Intent i = getIntent();
        Name=i.getStringExtra("Name");
        Gender=i.getStringExtra("Gender");
        if(Gender.equals("MALE")) {
            msg = "Welcome " + "Mr " + Name;
        }
        else {
            msg = "Welcome " + "Miss " + Name;
        }
        tvName.setText(msg);


        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(LocationServices.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);



        final ArrayList<String> HeightFeet = new ArrayList<String>();
        HeightFeet.add("1");
        HeightFeet.add("2");
        HeightFeet.add("3");
        HeightFeet.add("4");
        HeightFeet.add("5");
        HeightFeet.add("6");
        HeightFeet.add("7");
        HeightFeet.add("8");
        HeightFeet.add("9");
        HeightFeet.add("10");
        ArrayAdapter<String> HeightFeetAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, HeightFeet);
        spnHeightFeet.setAdapter(HeightFeetAdapter);


        final ArrayList<String> HeightInch = new ArrayList<String>();
        HeightInch.add("0");
        HeightInch.add("1");
        HeightInch.add("2");
        HeightInch.add("3");
        HeightInch.add("4");
        HeightInch.add("5");
        HeightInch.add("6");
        HeightInch.add("7");
        HeightInch.add("8");
        HeightInch.add("9");
        HeightInch.add("10");
        HeightInch.add("11");
        ArrayAdapter<String> HeightInchAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, HeightInch);
        spnHeightInch.setAdapter(HeightInchAdapter);


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etWeight.length()==0)
                {
                    etWeight.setError("Please enter some weight");

                }
                else {
                    int id = spnHeightFeet.getSelectedItemPosition();
                    String s = HeightFeet.get(id);
                    feet = Integer.parseInt(s);
                    int id1 = spnHeightInch.getSelectedItemPosition();
                    String s1 = HeightInch.get(id);
                    inch = Integer.parseInt(s1);
                    Weight = Double.parseDouble(etWeight.getText().toString());
                    Height = (feet * 0.3) + (inch * 0.025);

                    bmi = ((double) Weight) / (Height * Height);
                    String mi = String.valueOf(bmi);

                    Intent intent = new Intent(WelcomeActivity.this, SubmitActivity.class);
                    intent.putExtra("bmi", mi);
                    startActivity(intent);
                }


            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WelcomeActivity.this, HistoryActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.website) {
            Intent i = new Intent((Intent.ACTION_VIEW));
            i.setData(Uri.parse("http://" + "www.Instagram.com"));
            startActivity(i);
        }
        if (item.getItemId() == R.id.about) {
            Toast.makeText(this, "APP Developed by Mr.Tarun Methwani", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }




class MyTask extends AsyncTask<String,Void,Double>
    {

        @Override
        protected Double doInBackground(String... strings) {
            double temp=0.0;
            String line="",json="";
            try
            {
                URL url =new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.connect();

                InputStreamReader isr=new InputStreamReader(con.getInputStream());
                BufferedReader br=new BufferedReader(isr);

                while((line =br.readLine())!=null)
                {
                    json=json + line + "\n";
                }
                JSONObject o=new JSONObject(json);
                JSONObject p=o.getJSONObject("main");
                temp=p.getDouble("temp");


            }
            catch( Exception e)
            {

            }
            return temp;
        }


        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            msgT=aDouble.toString();
            String m="\u00b0";
            String m1=msgT+m+"C";
            tvTemperature.setText(m1);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        if(gac!=null)
            gac.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(gac!=null)
            gac.disconnect();
    }


    @Override
    public void onConnected(Bundle bundle) {

        loc=LocationServices.FusedLocationApi.getLastLocation(gac);
        if(loc!=null)
        {
            double lat=loc.getLatitude();
            double lon=loc.getLongitude();
            Geocoder g=new Geocoder(this, Locale.ENGLISH);

            try {
                List<Address> la= null;
                la = g.getFromLocation(lat,lon,1);
                android.location.Address add =la.get(0);
                msgL=add.getLocality();
                tvLocation.setText(msgL);
                String c = msgL;
                String url = "http://api.openweathermap.org/";
                String sp = "data/2.5/weather?units=metric";
                String qu = "&q=" + c;
                String id = "e4c4aadb5cf75c2f2bc18a4e2ce8ce76";
                String m = url + sp + qu + "&appid=" + id;


                MyTask t = new MyTask();
                t.execute(m);



            } catch (IOException e) {
                e.printStackTrace();
            }



        }
        else
        {
            Toast.makeText(this, "Please Turn ON the GPS...", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "CONNECTION SUSPENDED", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Toast.makeText(this, "CONNECTION FAILED", Toast.LENGTH_SHORT).show();
    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("DO YOU WANT TO EXIT");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(1);

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setNeutralButton("RATE US", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent inti=new Intent(WelcomeActivity.this,RatingActivity.class);
                startActivity(inti);
                finish();
            }
        });
        AlertDialog a = builder.create();
        a.setTitle("EXIT");
        a.show();
    }


}
