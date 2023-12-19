package com.home.calsi;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class Age extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //initializzation
    TextView todaydate;
    TextView todaymonth;
    TextView todayyear;
    TextView birthdate;
    TextView birthmonth;
    TextView birthyear;

    TextView ansdate;
    TextView ansmonth;
    TextView ansyear;
    TextView nextdate;
    TextView nextmonth;
    TextView nextyear;
    DatePickerDialog.OnDateSetListener setListener;
    int toyear;
    int tomonth;
    int todate;
    int biyear = 0;
    int bimonth = 0;
    int bidate = 0;
    int nyear;
    int nmonth;
    int ndate;
    InterstitialAd ad;

    //onclick lisneter of today change the date
    public void todays(View view) {
        //Theme_Holo_Dialog_MinWidth
        DatePickerDialog datePickerDialog = new DatePickerDialog(Age.this, android.R.style.Theme_Holo_Dialog_MinWidth, setListener, toyear, tomonth, todate);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    //onclick lisnter of birthday
    public void birth(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(Age.this, android.R.style.Theme_Holo_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                bidate = i2;
                bimonth = i1 + 1;
                biyear = i;
                birthdate.setText(String.valueOf(bidate));
                birthmonth.setText(String.valueOf(bimonth));
                birthyear.setText(String.valueOf(biyear));
            }
        }, toyear, tomonth, todate);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    public void calculate(View view){
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (bidate == 0 || bimonth == 0 || biyear == 0) {
            //No Date Specified
            Toast.makeText(this, "enter value", Toast.LENGTH_SHORT).show();
        }
        else {
            long totaltodays = toyear * 365 + tomonth * 30 + todate;
            long totalbidays = biyear * 365 + bimonth * 30 + bidate;
            int birthdaydate = (int) (totaltodays - totalbidays);
            int birthdayyear = (int) birthdaydate / 365;
            birthdaydate = birthdaydate - birthdayyear * 365;
            int birthdaymonth = (int) birthdaydate / 30;
            birthdaydate = birthdaydate - birthdaymonth * 30;

            ansdate.setText(String.valueOf(birthdaydate));
            ansmonth.setText(String.valueOf(birthdaymonth));
            ansyear.setText(String.valueOf(birthdayyear));

            if(tomonth<bimonth||(tomonth==bimonth&&todate<bidate)){
                ndate=bidate;
                nmonth=bimonth;
                nyear=toyear;
            }
            else{
                ndate=bidate;
                nmonth=bimonth;
                nyear=toyear+1;
            }

            totaltodays = nyear * 365 + nmonth * 30 + ndate;
            totalbidays = toyear * 365 + tomonth * 30 + todate;
            birthdaydate = (int) (totaltodays - totalbidays);
            birthdayyear = (int) birthdaydate / 365;
            birthdaydate = birthdaydate - birthdayyear * 365;
            birthdaymonth = (int) birthdaydate / 30;
            birthdaydate = birthdaydate - birthdaymonth * 30;

            nextdate.setText(String.valueOf(birthdaydate));
            nextmonth.setText(String.valueOf(birthdaymonth));
            nextyear.setText(String.valueOf(birthdayyear));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AudienceNetworkAds.initialize(this) ;
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        ad= new InterstitialAd(this);
        //ca-app-pub-3940256099942544/1033173712
        ad.setAdUnitId("ca-app-pub-3547026615546708/4268729212");
        ad.loadAd(new AdRequest.Builder().build());
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.agecalculate);
        todaydate = findViewById(R.id.todaydate);
        todaymonth = findViewById(R.id.todaymonth);
        todayyear = findViewById(R.id.todayyear);
        birthdate = findViewById(R.id.birthdate);
        birthmonth = findViewById(R.id.birthmonth);
        birthyear = findViewById(R.id.birthyear);
        ansdate = findViewById(R.id.ansday);
        ansmonth = findViewById(R.id.ansmonth);
        ansyear = findViewById(R.id.ansyear);
        nextdate = findViewById(R.id.nextday);
        nextmonth = findViewById(R.id.nextmonth);
        nextyear = findViewById(R.id.nextyear);
        Calendar calender = Calendar.getInstance();
        Date l = new Date(System.currentTimeMillis());
        toyear = 2020;
        tomonth = l.getMonth() + 1;
        todate = l.getDate();
        todaydate.setText(String.valueOf(todate));
        todaymonth.setText(String.valueOf(tomonth));
        todayyear.setText(String.valueOf((toyear)));
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                todate = i2;
                tomonth = i1 + 1;
                toyear = i;
                todaydate.setText(String.valueOf(todate));
                todaymonth.setText(String.valueOf(tomonth));
                todayyear.setText(String.valueOf((toyear)));
            }
        };
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(ad.isLoaded()){
                ad.show();
            }
            else{
                super.onBackPressed();
            }
            ad.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    Age.super.onBackPressed();
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Share) {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.home.calsi");
            intent.putExtra(Intent.EXTRA_SUBJECT,"Calsi- A Calculator app");
            startActivity(Intent.createChooser(intent,"Share Using"));
        }
        else if (id == R.id.about) {
            Intent intent = new Intent(getApplicationContext(),About.class);
            startActivity(intent);
        }
        else if (id == R.id.help) {
            Intent intent=new Intent(getApplicationContext(),Help.class);
            startActivity(intent);
        }
        else if (id == R.id.Rate) {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.home.calsi"));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.simplecalculate) {
            finish();
        }
        else if (id == R.id.unitcalculate) {
            finish();
            Intent intent=new Intent(getApplicationContext(),UnitConvertor.class);
            startActivity(intent);
        }
        else if (id == R.id.agecalculate) { }
        else if (id == R.id.percentagecalculate) {
            finish();
            Intent intent=new Intent(getApplicationContext(),Percentage.class);
            startActivity(intent);

        }
        else if (id == R.id.healthcalculate) {
            finish();
            Intent intent=new Intent(getApplicationContext(),HealthCalculator.class);
            startActivity(intent);

        }
        else if (id == R.id.emicalculate) {
            finish();
            Intent intent=new Intent(getApplicationContext(),EMI.class);
            startActivity(intent);

        }
        else if (id == R.id.gstcalculate) {
            finish();
            Intent intent=new Intent(getApplicationContext(),GstCalculator.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_share) {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.home.calsi");
            intent.putExtra(Intent.EXTRA_SUBJECT,"Calsi- A Calculator app");
            startActivity(Intent.createChooser(intent,"Share Using"));
        }
        else if (id == R.id.help) {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.home.calsi"));
            startActivity(intent);
        }
        else if(id==R.id.about){
            Intent intent = new Intent(getApplicationContext(),About.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
