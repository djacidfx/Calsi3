package com.home.calsi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Help extends AppCompatActivity {
    InterstitialAd ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        AudienceNetworkAds.initialize(this) ;
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView2 = findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest2);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        ad= new InterstitialAd(this);
        //ca-app-pub-3940256099942544/1033173712
        ad.setAdUnitId("ca-app-pub-3547026615546708/4268729212");
        ad.loadAd(new AdRequest.Builder().build());

        ImageView image=findViewById(R.id.image);
        image.setImageResource(R.drawable.help);
    }
    public void onBackPressed() {
        if (ad.isLoaded()) {
            ad.show();
        }
        else {
            super.onBackPressed();
        }
        ad.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Help.super.onBackPressed();
            }
        });
    }
}