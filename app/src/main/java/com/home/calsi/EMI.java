package com.home.calsi;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EMI extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    EditText loan;
    EditText interest;
    EditText year;
    EditText month;
    TextView emi;
    TextView totalinterest;
    TextView total;
    double loanamount;
    double rate;
    double sal;
    double mahine;
    double emidouble;
    double tinterest;
    double totaldouble;
    double period;
    InterstitialAd ad;
    public void calculate(View view){
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if(loan.getText().toString().isEmpty()||interest.getText().toString().isEmpty()||(year.getText().toString().isEmpty()&&month.getText().toString().isEmpty())){
            Toast.makeText(this,"enter ", Toast.LENGTH_SHORT).show();
        }
        else{
            if(month.getText().toString().isEmpty()){
                mahine=0;
            }
            else{
                mahine=Double.valueOf(month.getText().toString());
            }
            if(year.getText().toString().isEmpty()){
                sal=0;
            }
            else{
                sal=Double.valueOf(year.getText().toString());
            }

            loanamount=Double.valueOf(loan.getText().toString());
            rate=Double.valueOf(interest.getText().toString());
            rate=rate/(12*100);
            period=sal*12+mahine;
            double pmt= loanamount*(rate)/(1-Math.pow(1+rate,-period));
            tinterest=pmt*period-loanamount;
            totaldouble=tinterest+loanamount;
            double p=Math.pow(1+rate,period);
            emidouble=loanamount*rate*p/(p-1);
            if ((int) tinterest == tinterest) {
                int tinterest = (int) this.tinterest;
                totalinterest.setText(String.valueOf(tinterest));
            } else {
                BigDecimal bd = new BigDecimal(tinterest).setScale(2, RoundingMode.HALF_UP);
                tinterest = bd.doubleValue();
                totalinterest.setText(String.valueOf(tinterest));
            }
            if ((int) totaldouble == totaldouble) {
                int totaldouble = (int) this.totaldouble;
                total.setText(String.valueOf(totaldouble));
            } else {
                BigDecimal bd = new BigDecimal(totaldouble).setScale(2, RoundingMode.HALF_UP);
                totaldouble = bd.doubleValue();
                total.setText(String.valueOf(totaldouble));
            }
            if ((int) totaldouble == totaldouble) {
                int totaldouble = (int) this.totaldouble;
                total.setText(String.valueOf(totaldouble));
            } else {
                BigDecimal bd = new BigDecimal(totaldouble).setScale(2, RoundingMode.HALF_UP);
                totaldouble = bd.doubleValue();
                total.setText(String.valueOf(totaldouble));
            }
            if ((int) emidouble == emidouble) {
                int emidouble = (int) this.emidouble;
                emi.setText(String.valueOf(emidouble));
            } else {
                BigDecimal bd = new BigDecimal(emidouble).setScale(2, RoundingMode.HALF_UP);
                emidouble = bd.doubleValue();
                emi.setText(String.valueOf(emidouble));
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi);
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
        navigationView.setCheckedItem(R.id.emicalculate);
        loan=findViewById(R.id.loan);
        interest=findViewById(R.id.interest);
        year=findViewById(R.id.year);
        month=findViewById(R.id.month);
        emi=findViewById(R.id.emi);
        totalinterest=findViewById(R.id.totalinterest);
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
                    EMI.super.onBackPressed();
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

        } else if (id == R.id.healthcalculate) {
            finish();
            Intent intent=new Intent(getApplicationContext(),HealthCalculator.class);
            startActivity(intent);
        }
        else if (id == R.id.emicalculate) { }
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
