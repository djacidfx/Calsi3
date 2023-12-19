package com.home.calsi;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class About extends AppCompatActivity {
    InterstitialAd ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
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
        //ca-app-pub-3940256099942544/1033173712  facebookid 695540671178111_695574431174735
        ad.setAdUnitId("ca-app-pub-3547026615546708/4268729212");

        ad.loadAd(new AdRequest.Builder().build());

        TextView  text1=findViewById(R.id.text1);
        TextView  text2=findViewById(R.id.text2);
        TextView  text3=findViewById(R.id.text3);
        TextView  text4=findViewById(R.id.text4);
        TextView  text5=findViewById(R.id.text5);
        TextView  text6=findViewById(R.id.text6);
        TextView  text7=findViewById(R.id.text7);
        TextView  text8=findViewById(R.id.text8);
        TextView  text9=findViewById(R.id.text9);
        TextView  text10=findViewById(R.id.text10);

        String text ="The team of Calsi focuses on providing the user a good calculation experience. The smooth interface and its design makes the app very handy and user friendly." +
                "We have added some scientific calculation function, for effective calculation. The app provides all the essential features which an user encounters in his daily life";
        text1.setText(text);
        text ="List of some features that are currently supported:";
        text2.setText(text);
        text ="Gives instant results has user type.\nSupports some fundamental functions such as trigonometric, exponential, logarithmic and some of the arithmetic operations." ;
        text3.setText(text);
        text ="Supports Length, Area, Weight, Temperature, Time, Speed, Angle and Data.\nAll Conversion is displayed simultaneously which makes it look unique.";
        text4.setText(text);
        text ="The user are able to calculate their Age.\nWith the help of this calculator user can find out how many year, month and days are for the users next birthday.";
        text5.setText(text);
        text ="Health is calculated on the basis of users BMI(Body Mass Index).\nThe Users can also see their ideal weight.";
        text6.setText(text);
        text ="By entering the Loan amount, Rate of interest and Period users can calculate Monthly EMI along with total interest and total payment.";
        text7.setText(text);
        text ="By entering the subject marks users can verify their results.";
        text8.setText(text);
        text ="This calculator is designed to calculate the Inclusive GST tax, Exclusive GST tax and Tax value for a given amount while allowing user to specify the tax percentage.\n" +
                "By entering your Initial amount and Rate of GST users can calculate Net amount along with GST amount and  Total amount.";
        text9.setText(text);
        text ="Any Quires and suggestions are welcomed on feedback.arpa@gmail.com .";
        text10.setText(text);

    }
    public void onBackPressed(){
        if(ad.isLoaded()){
            ad.show();
        }
        else{
            super.onBackPressed();
        }
        ad.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                About.super.onBackPressed();
            }
        });

    }
}