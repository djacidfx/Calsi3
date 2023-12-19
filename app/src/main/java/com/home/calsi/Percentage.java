package com.home.calsi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Percentage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView total1;
    TextView total2;
    TextView total3;
    TextView total4;
    TextView total5;
    TextView total6;
    TextView total7;
    TextView total8;
    TextView total9;

    TextView got1;
    TextView got2;
    TextView got3;
    TextView got4;
    TextView got5;
    TextView got6;
    TextView got7;
    TextView got8;
    TextView got9;

    TextView percentage;
    TextView obtained;
    TextView total;
    TextView grade;

    String [] totalmarks=new String[9];
    String [] obtainmarks=new String[9];
    double percent;
    double obtain;
    double t;
    String g;
    InterstitialAd ad;

    public void calculate(View view){
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        percent=0;
        obtain=0;
        t=0;
        totalmarks[0]=total1.getText().toString();
        totalmarks[1]=total2.getText().toString();
        totalmarks[2]=total3.getText().toString();
        totalmarks[3]=total4.getText().toString();
        totalmarks[4]=total5.getText().toString();
        totalmarks[5]=total6.getText().toString();
        totalmarks[6]=total7.getText().toString();
        totalmarks[7]=total8.getText().toString();
        totalmarks[8]=total9.getText().toString();
        for(int i=0;i<9;i++){
            if(totalmarks[i].isEmpty()){
                totalmarks[i]="0";
            }
        }
        obtainmarks[0]=got1.getText().toString();
        obtainmarks[1]=got2.getText().toString();
        obtainmarks[2]=got3.getText().toString();
        obtainmarks[3]=got4.getText().toString();
        obtainmarks[4]=got5.getText().toString();
        obtainmarks[5]=got6.getText().toString();
        obtainmarks[6]=got7.getText().toString();
        obtainmarks[7]=got8.getText().toString();
        obtainmarks[8]=got9.getText().toString();
        for(int i=0;i<9;i++){
            if(obtainmarks[i].isEmpty()){
                obtainmarks[i]="0";
            }
        }

        for(int i=0;i<9;i++){
            obtain+=Double.valueOf(obtainmarks[i]);
        }
        for(int i=0;i<9;i++){
            t+=Double.valueOf(totalmarks[i]);
        }
        percent=obtain/t*100;
        if(percent>=75){
            g="distinction";
        }
        else if(percent>=65){
            g="A";
        }
        else if(percent>=55){
            g="B";
        }
        else if(percent>=35){
            g="C";
        }
        else if(percent<35){
            g="FAIL";
            percentage.setTextColor(Color.parseColor("#ffcc0000"));
            obtained.setTextColor(Color.parseColor("#ffcc0000"));
            total.setTextColor(Color.parseColor("#ffcc0000"));
        }
        grade.setText(g);
        if ((int)percent == percent) {
            int percent = (int) this.percent;
            percentage.setText(String.valueOf(percent)+"%");
        }
        else {
            BigDecimal bd = new BigDecimal(percent).setScale(2, RoundingMode.HALF_UP);
            percent = bd.doubleValue();
            percentage.setText(String.valueOf(percent)+"%");
        }
        obtained.setText(String.valueOf(obtain));
        total.setText(String.valueOf(t));


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage);
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
        navigationView.setCheckedItem(R.id.percentagecalculate);
        total=findViewById(R.id.totalmarks);
        percentage=findViewById(R.id.percentage);
        obtained=findViewById(R.id.obtainedmarks);
        grade=findViewById(R.id.grades);


        total1=findViewById(R.id.total1);
        total2=findViewById(R.id.total2);
        total3=findViewById(R.id.total3);
        total4=findViewById(R.id.total4);
        total5=findViewById(R.id.total5);
        total6=findViewById(R.id.total6);
        total7=findViewById(R.id.total7);
        total8=findViewById(R.id.total8);
        total9=findViewById(R.id.total9);
        got1=findViewById(R.id.got1);
        got2=findViewById(R.id.got2);
        got3=findViewById(R.id.got3);
        got4=findViewById(R.id.got4);
        got5=findViewById(R.id.got5);
        got6=findViewById(R.id.got6);
        got7=findViewById(R.id.got7);
        got8=findViewById(R.id.got8);
        got9=findViewById(R.id.got9);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if(ad.isLoaded()){
                ad.show();
            }
            else{
                super.onBackPressed();
            }
            ad.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    Percentage.super.onBackPressed();
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
            intent.putExtra(Intent.EXTRA_SUBJECT,"Calsi-A Calculator app");
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
        else if (id == R.id.agecalculate) {
            finish();
            Intent intent=new Intent(getApplicationContext(),Age.class);
            startActivity(intent);

        }
        else if (id == R.id.percentagecalculate) { }
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
