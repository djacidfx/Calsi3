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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HealthCalculator extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    TextView bmi;
    TextView ideal;
    TextView msg;
    EditText height;
    EditText weight;
    double length;
    double mass;
    double bmindex;
    ImageView image;
    InterstitialAd ad;

    public void calculate(View view){
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if(height.getText().toString().isEmpty()||weight.getText().toString().isEmpty()){
            Toast.makeText(this,"enter inputs",Toast.LENGTH_SHORT).show();
        }
        else{
            length=Double.valueOf(height.getText().toString().trim());
            mass=Double.valueOf(weight.getText().toString().trim());
            bmindex=mass/((length*length)/10000);
            if ((int) bmindex == bmindex) {
                int store = (int) this.bmindex;
                bmi.setText(String.valueOf(bmindex));
            } else {
                BigDecimal bd = new BigDecimal(bmindex).setScale(2, RoundingMode.HALF_UP);
                bmindex = bd.doubleValue();
                bmi.setText(String.valueOf(bmindex));
            }

            if(bmindex<=18.5){
                image.setImageResource(R.drawable.weekman);
                bmi.setTextColor(Color.parseColor("#f17a0a"));
                msg.setTextColor(Color.parseColor("#f17a0a"));
                msg.setText("Need to consume a bite");
            }
            else if(bmindex>18.5&&bmindex<=24.9){
                image.setImageResource(R.drawable.fitman);
                bmi.setTextColor(Color.parseColor("#558b2f"));
                msg.setTextColor(Color.parseColor("#558b2f"));
                msg.setText("Well maintained your Body");
            }
            else if(bmindex>=25){
                image.setImageResource(R.drawable.fatman);
                bmi.setTextColor(Color.parseColor("#ffcc0000"));
                msg.setTextColor(Color.parseColor("#ffcc0000"));
                msg.setText("Need to workout");
            }
            mass=(length*length)/10000*21.7;
            if ((int) mass == mass) {
                int mass = (int) this.mass;
                ideal.setText(String.valueOf(mass));
            } else {
                BigDecimal bd = new BigDecimal(mass).setScale(2, RoundingMode.HALF_UP);
                mass = bd.doubleValue();
                ideal.setText(String.valueOf(mass));
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_calculator);
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
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.healthcalculate);
        bmi=findViewById(R.id.displaybmi);
        ideal=findViewById(R.id.idealweight);
        msg=findViewById(R.id.msgtext);
        height=findViewById(R.id.heightText);
        weight=findViewById(R.id.weightText);
        image=findViewById(R.id.imagedisplay);
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
                    HealthCalculator.super.onBackPressed();
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
        else if (id == R.id.healthcalculate) { }
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
