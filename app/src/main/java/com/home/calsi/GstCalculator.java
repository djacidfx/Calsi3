package com.home.calsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class GstCalculator extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {
    Spinner spiner;
    List<String> spinner=new ArrayList<String>();
    String choosen="GST Inclusive";
    TextView amount;
    TextView gstrate;
    TextView net;
    TextView gstamount;
    TextView total;
    double am;
    double gst;
    double n;
    double gsta;
    double t;
    InterstitialAd ad;
    public void calculate(View view){
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        calculation();
    }
    public void calculation(){
        if(String.valueOf(amount.getText()).isEmpty()||String.valueOf(amount.getText()).isEmpty()){
            Toast.makeText(this,"Enter the value", Toast.LENGTH_SHORT).show();
        }
        else {
            am = Double.valueOf(String.valueOf(amount.getText()));
            gst = Double.valueOf(String.valueOf(gstrate.getText()));
            if (choosen.contentEquals("GST Inclusive")) {
                t=am;
                gsta=(gst*am)/(100+gst);
                n=am-gsta;
            }
            else {
                gsta=gst*am/100;
                n=am;
                t=am+gsta;

            }
            if ((int) gsta== gsta) {
                int gsta = (int) this.gsta;
                gstamount.setText(String.valueOf(gsta));
            }
            else {
                BigDecimal bd = new BigDecimal(gsta).setScale(2, RoundingMode.HALF_UP);
                gsta = bd.doubleValue();
                gstamount.setText(String.valueOf(gsta));
            }
            if ((int) n== n) {
                int n = (int) this.n;
                net.setText(String.valueOf(n));
            }
            else {
                BigDecimal bd = new BigDecimal(n).setScale(2, RoundingMode.HALF_UP);
                n = bd.doubleValue();
                net.setText(String.valueOf(n));
            }
            if ((int) t== t) {
                int t = (int) this.t;
                total.setText(String.valueOf(t));
            }
            else {
                BigDecimal bd = new BigDecimal(t).setScale(2, RoundingMode.HALF_UP);
                t = bd.doubleValue();
                total.setText(String.valueOf(t));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gst_calculator);
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
        spiner =(Spinner)findViewById(R.id.spinner);
        spinner.add("GST Inclusive");
        spinner.add("GST Exclusive");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinner);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(dataAdapter);
        spiner.setOnItemSelectedListener(this);
        amount=findViewById(R.id.amount);
        gstrate=findViewById(R.id.gstrate);
        net=findViewById(R.id.netamount);
        gstamount=findViewById(R.id.gstamount);
        total=findViewById(R.id.total);
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
                    GstCalculator.super.onBackPressed();
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
        else if (id == R.id.gstcalculate) { }
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        choosen =adapterView.getItemAtPosition(i).toString();
        calculation();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}