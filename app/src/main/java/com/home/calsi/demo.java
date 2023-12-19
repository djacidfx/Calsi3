package com.home.calsi;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class demo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
        String[] a=new String[9];
        String name;
        double[] u=new double[9];
        String choosen="yard";
        ListView lists;
        ArrayAdapter <String> adapter;
        String measurement="0";
        TextView digits;
        Spinner spiner;
        List<String> units=new ArrayList<String>();
        List<String> spinner=new ArrayList<String>();
        boolean using=false;
        InterstitialAd ad;

    public void calculate(View view) {
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if(!(digits.getText().toString().isEmpty())) {
            measurement = digits.getText().toString();
            set();
        }
        else{
            digits.setText("0");
            measurement="0";
            set();
        }
    }

    public void length(){
        if(choosen.contentEquals("m")){
            u[0]=Double.valueOf(measurement)*1000;
            u[1]=Double.valueOf(measurement)*100;
            u[2]=Double.valueOf(measurement)*1;
            u[3]=Double.valueOf(measurement)*0.001;
            u[4]=Double.valueOf(measurement)*39.37007;
            u[5]=Double.valueOf(measurement)*3.2808;
            u[6]=Double.valueOf(measurement)*0.00062137;
            u[7]=Double.valueOf(measurement)*1.0936;
        }
        //mm cm m km inch ft mile yard
        else if(choosen.contentEquals("mm")){
            u[0]=Double.valueOf(measurement)*1;
            u[1]=Double.valueOf(measurement)*0.1;
            u[2]=Double.valueOf(measurement)*0.001;
            u[3]=Double.valueOf(measurement)*0.001/1000;
            u[4]=Double.valueOf(measurement)*39.37007/1000;
            u[5]=Double.valueOf(measurement)*3.2808/1000;
            u[6]=Double.valueOf(measurement)*0.00062137/1000;
            u[7]=Double.valueOf(measurement)*1.0936/1000;

        }
        else if(choosen.contentEquals("cm")){
            u[0]=Double.valueOf(measurement)*1000/100;
            u[1]=Double.valueOf(measurement)*1;
            u[2]=Double.valueOf(measurement)*1/100;
            u[3]=Double.valueOf(measurement)*0.001/100;
            u[4]=Double.valueOf(measurement)*39.37007/100;
            u[5]=Double.valueOf(measurement)*3.2808/100;
            u[6]=Double.valueOf(measurement)*0.00062137/100;
            u[7]=Double.valueOf(measurement)*1.0936/100;

        }
        else if(choosen.contentEquals("km")){
            u[0]=Double.valueOf(measurement)*1000*1000;
            u[1]=Double.valueOf(measurement)*100*1000;
            u[2]=Double.valueOf(measurement)*1000;
            u[3]=Double.valueOf(measurement)*0.001*1000;
            u[4]=Double.valueOf(measurement)*39.37007*1000;
            u[5]=Double.valueOf(measurement)*3.2808*1000;
            u[6]=Double.valueOf(measurement)*0.00062137*1000;
            u[7]=Double.valueOf(measurement)*1.0936*1000;

        }
        else if(choosen.contentEquals("inch")){
            u[0]=Double.valueOf(measurement)*25.4;
            u[1]=Double.valueOf(measurement)*2.54;
            u[2]=Double.valueOf(measurement)*0.0254;
            u[3]=Double.valueOf(measurement)*0.0254/1000;
            u[4]=Double.valueOf(measurement)*1;
            u[5]=Double.valueOf(measurement)*0.8333;
            u[6]=Double.valueOf(measurement)*1.5782;
            u[7]=Double.valueOf(measurement)*0.2778;

        }
        else if(choosen.contentEquals("ft")){
            u[0]=Double.valueOf(measurement)*304.8;
            u[1]=Double.valueOf(measurement)*304.8/10;
            u[2]=Double.valueOf(measurement)*304.8/1000;
            u[3]=Double.valueOf(measurement)*0.3048/1000;
            u[4]=Double.valueOf(measurement)*12;
            u[5]=Double.valueOf(measurement)*1;
            u[6]=Double.valueOf(measurement)*0.000189;
            u[7]=Double.valueOf(measurement)*0.333;

        }
        else if(choosen.contentEquals("mile")){
            u[0]=Double.valueOf(measurement)*1609344;
            u[1]=Double.valueOf(measurement)*1609344/10;
            u[2]=Double.valueOf(measurement)*1609.344;
            u[3]=Double.valueOf(measurement)*1609.344/1000;
            u[4]=Double.valueOf(measurement)*63360;
            u[5]=Double.valueOf(measurement)*5280;
            u[6]=Double.valueOf(measurement)*1;
            u[7]=Double.valueOf(measurement)*1760;

        }
        else {
            u[0]=Double.valueOf(measurement)*914.4;
            u[1]=Double.valueOf(measurement)*914.4/10;
            u[2]=Double.valueOf(measurement)*0.9144;
            u[3]=Double.valueOf(measurement)*0.9144/1000;
            u[4]=Double.valueOf(measurement)*36;
            u[5]=Double.valueOf(measurement)*3;
            u[6]=Double.valueOf(measurement)*0.000568;
            u[7]=Double.valueOf(measurement)*1;
        }
        //end
        for(int i=0;i<=7;i++){
            if ((int) u[i] == u[i]) {
                int store = (int) u[i];
                a[i]=String.valueOf(store);
            }
            else {
                if((int) u[i]==0){
                    a[i]=String.valueOf(u[i]);
                }else{
                    BigDecimal bd = new BigDecimal(u[i]).setScale(2, RoundingMode.HALF_UP);
                    u[i] = bd.doubleValue();
                    a[i]=String.valueOf(u[i]);
                }
            }
        }
        if(!using) {
            using=true;
            //mm cm m km inch ft mile yard
            units.add(0, a[0] + " mm");
            units.add(1, a[1] + " cm");
            units.add(2, a[2] + " m");
            units.add(3, a[3] + " km");
            units.add(4, a[4] + " inch");
            units.add(5, a[5] + " ft");
            units.add(6, a[6] + " mile");
            units.add(7, a[7] + " yard");
        }
        else{
            units.set(0, a[0] + " mm");
            units.set(1, a[1] + " cm");
            units.set(2, a[2] + " m");
            units.set(3, a[3] + " km");
            units.set(4, a[4] + " inch");
            units.set(5, a[5] + " ft");
            units.set(6, a[6] + " mile");
            units.set(7, a[7] + " yard");
        }
        adapter =new ArrayAdapter<String>(this,R.layout.align_right,units);
        lists.setAdapter(adapter);

    }

    public void area(){
        //start
        if(choosen.contentEquals("m\u00B2")){
            u[0]=Double.valueOf(measurement)*1000000;
            u[1]=Double.valueOf(measurement)*1000;
            u[2]=Double.valueOf(measurement)*1;
            u[3]=Double.valueOf(measurement)*0.001*0.001;
            u[4]=Double.valueOf(measurement)*39.37007*39.37007;
            u[5]=Double.valueOf(measurement)*3.2808*3.2808;
            u[6]=Double.valueOf(measurement)*0.00062137*0.00062137;
            u[7]=Double.valueOf(measurement)*1.0936*1.0936;
        }
        else if(choosen.contentEquals("mm\u00B2")){
            u[0]=Double.valueOf(measurement)*1;
            u[1]=Double.valueOf(measurement)*0.1*0.1;
            u[2]=Double.valueOf(measurement)*0.01*0.01;
            u[3]=Double.valueOf(measurement)*0.001/100*0.001/100;
            u[4]=Double.valueOf(measurement)*39.37007/100*39.37007/100;
            u[5]=Double.valueOf(measurement)*3.2808/100*3.2808/100;
            u[6]=Double.valueOf(measurement)*0.00062137/100*0.00062137/100;
            u[7]=Double.valueOf(measurement)*1.0936/100*1.0936/100;

        }
        else if(choosen.contentEquals("cm\u00B2")){
            u[0]=Double.valueOf(measurement)*10*10;
            u[1]=Double.valueOf(measurement)*1;
            u[2]=Double.valueOf(measurement)*0.01*0.01;
            u[3]=Double.valueOf(measurement)*0.001/100*0.001/100;
            u[4]=Double.valueOf(measurement)*39.37007/100*39.37007/100;
            u[5]=Double.valueOf(measurement)*3.2808/100*3.2808/100;
            u[6]=Double.valueOf(measurement)*0.00062137/100*0.00062137/100;
            u[7]=Double.valueOf(measurement)*1.0936/100*1.0936/100;

        }
        else if(choosen.contentEquals("km\u00B2")){
            u[0]=Double.valueOf(measurement)* 1000000*1000000;
            u[1]=Double.valueOf(measurement)*100000*100000;
            u[2]=Double.valueOf(measurement)*1000*1000;
            u[3]=Double.valueOf(measurement)*1;
            u[4]=Double.valueOf(measurement)*39.37007*1000*39.37007*1000;
            u[5]=Double.valueOf(measurement)*3.2808*1000*3.2808*1000;
            u[6]=Double.valueOf(measurement)*0.00062137*1000*0.00062137*1000;
            u[7]=Double.valueOf(measurement)*1.0936*1000*1.0936*1000;

        }
        else if(choosen.contentEquals("inch\u00B2")){
            u[0]=Double.valueOf(measurement)*25.4*25.4;
            u[1]=Double.valueOf(measurement)*2.54*2.54;
            u[2]=Double.valueOf(measurement)*0.0254*0.0254;
            u[3]=Double.valueOf(measurement)*0.0254/1000*0.0254/1000;
            u[4]=Double.valueOf(measurement)*1;
            u[5]=Double.valueOf(measurement)*0.8333*0.8333;
            u[6]=Double.valueOf(measurement)*1.5782*1.5782;
            u[7]=Double.valueOf(measurement)*0.2778*0.2778;

        }
        else if(choosen.contentEquals("ft\u00B2")){
            u[0]=Double.valueOf(measurement)*304.8*304.8;
            u[1]=Double.valueOf(measurement)*30.48*30.48;
            u[2]=Double.valueOf(measurement)*304.8/1000*304.8/1000;
            u[3]=Double.valueOf(measurement)*0.3048/1000*0.3048/1000;
            u[4]=Double.valueOf(measurement)*144;
            u[5]=Double.valueOf(measurement)*1;
            u[6]=Double.valueOf(measurement)*0.000189*0.000189;
            u[7]=Double.valueOf(measurement)*0.333*0.333;

        }
        else if(choosen.contentEquals("acre\u00B2")){
            u[0]=Double.valueOf(measurement)*1609344*1609344;
            u[1]=Double.valueOf(measurement)*160934.4*160934.4;
            u[2]=Double.valueOf(measurement)*1609.344*1609.344;
            u[3]=Double.valueOf(measurement)*1609.344/1000*1609.344/1000;
            u[4]=Double.valueOf(measurement)*63360*63360;
            u[5]=Double.valueOf(measurement)*5280*5280;
            u[6]=Double.valueOf(measurement)*1;
            u[7]=Double.valueOf(measurement)*1760*1760;

        }
        else {
            u[0]=Double.valueOf(measurement)*914.4*914.4;
            u[1]=Double.valueOf(measurement)*91.44*91.44;
            u[2]=Double.valueOf(measurement)*0.9144*0.9144;
            u[3]=Double.valueOf(measurement)*0.9144/1000*0.9144/1000;
            u[4]=Double.valueOf(measurement)*36*36;
            u[5]=Double.valueOf(measurement)*9;
            u[6]=Double.valueOf(measurement)*0.000568*0.000568;
            u[7]=Double.valueOf(measurement)*1;
        }
        //end
        for(int i=0;i<=7;i++){
            if ((int) u[i] == u[i]) {
                int store = (int) u[i];
                a[i]=String.valueOf(store);
            }
            else {
                if((int) u[i]==0){
                    a[i]=String.valueOf(u[i]);
                }else{
                    BigDecimal bd = new BigDecimal(u[i]).setScale(2, RoundingMode.HALF_UP);
                    u[i] = bd.doubleValue();
                    a[i]=String.valueOf(u[i]);
                }
            }
        }
        if(!using) {
            using=true;
            units.add(0, a[0] + " mm\u00B2");
            units.add(1, a[1] + " cm\u00B2");
            units.add(2, a[2] + " m\u00B2");
            units.add(3, a[3] + " km\u00B2");
            units.add(4, a[4] + " inch\u00B2");
            units.add(5, a[5] + " ft\u00B2");
            units.add(6, a[6] + " acre");
            units.add(7, a[7] + " yard\u00B2");
        }
        else {
            units.set(0, a[0] + " mm\u00B2");
            units.set(1, a[1] + " cm\u00B2");
            units.set(2, a[2] + " m\u00B2");
            units.set(3, a[3] + " km\u00B2");
            units.set(4, a[4] + " inch\u00B2");
            units.set(5, a[5] + " ft\u00B2");
            units.set(6, a[6] + " acre");
            units.set(7, a[7] + " yard\u00B2");
        }
        adapter =new ArrayAdapter<String>(this,R.layout.align_right,units);
        lists.setAdapter(adapter);
    }

    public void clock(){
        //start
        if(choosen.contentEquals("sec")){
            u[0]=Double.valueOf(measurement)*1000;
            u[1]=Double.valueOf(measurement)*1;
            u[2]=Double.valueOf(measurement)*1/60;
            u[3]=Double.valueOf(measurement)*1/(60*60);
            u[4]=Double.valueOf(measurement)*1/(60*60*24);
            u[5]=Double.valueOf(measurement)*1/(60*60*24*7);
            u[6]=Double.valueOf(measurement)*1/(60*60*24*30);
            u[7]=Double.valueOf(measurement)*1/(60*60*24*365);
        }
        else if(choosen.contentEquals("ms")){
            u[0]=Double.valueOf(measurement)*1;
            u[1]=Double.valueOf(measurement)*0.001;
            u[2]=Double.valueOf(measurement)*0.001/60;
            u[3]=Double.valueOf(measurement)*0.001/(60*60);
            u[4]=Double.valueOf(measurement)*0.001/(60*60*24);
            u[5]=Double.valueOf(measurement)*0.001/(60*60*24*7);
            u[6]=Double.valueOf(measurement)*0.001/(60*60*24*30);
            u[7]=Double.valueOf(measurement)*0.001/(60*60*24*365);

        }
        else if(choosen.contentEquals("min")){
            u[0]=Double.valueOf(measurement)*60000;
            u[1]=Double.valueOf(measurement)*60;
            u[2]=Double.valueOf(measurement)*1;
            u[3]=Double.valueOf(measurement)*1/60;
            u[4]=Double.valueOf(measurement)*1/(60*24);
            u[5]=Double.valueOf(measurement)*1/(60*24*7);
            u[6]=Double.valueOf(measurement)*1/(60*24*30);
            u[7]=Double.valueOf(measurement)*1/(60*24*365);

        }
        else if(choosen.contentEquals("hour")){
            u[0]=Double.valueOf(measurement)*60*60*1000;
            u[1]=Double.valueOf(measurement)*60*60;
            u[2]=Double.valueOf(measurement)*60;
            u[3]=Double.valueOf(measurement)*1;
            u[4]=Double.valueOf(measurement)*1/24;
            u[5]=Double.valueOf(measurement)*1/(24*7);
            u[6]=Double.valueOf(measurement)*1/(24*30);
            u[7]=Double.valueOf(measurement)*1/(24*365);

        }
        else if(choosen.contentEquals("day")){
            u[0]=Double.valueOf(measurement)*60*60*1000*24;
            u[1]=Double.valueOf(measurement)*60*60*24;
            u[2]=Double.valueOf(measurement)*60*24;
            u[3]=Double.valueOf(measurement)*24;
            u[4]=Double.valueOf(measurement)*1;
            u[5]=Double.valueOf(measurement)*1/7;
            u[6]=Double.valueOf(measurement)*1/30;
            u[7]=Double.valueOf(measurement)*1/365;

        }
        else if(choosen.contentEquals("week")){
            u[0]=Double.valueOf(measurement)*60*60*24*1000*7;
            u[1]=Double.valueOf(measurement)*60*60*24*7;
            u[2]=Double.valueOf(measurement)*60*24*7;
            u[3]=Double.valueOf(measurement)*24*7;
            u[4]=Double.valueOf(measurement)*7;
            u[5]=Double.valueOf(measurement)*1;
            u[6]=Double.valueOf(measurement)*0.23;
            u[7]=Double.valueOf(measurement)*0.0192;

        }
        else if(choosen.contentEquals("month")){
            u[0]=Double.valueOf(measurement)*60*60*24*1000*30;
            u[1]=Double.valueOf(measurement)*60*60*24*30;
            u[2]=Double.valueOf(measurement)*60*24*30;
            u[3]=Double.valueOf(measurement)*24*30;
            u[4]=Double.valueOf(measurement)*30;
            u[5]=Double.valueOf(measurement)*4.345;
            u[6]=Double.valueOf(measurement)*1;
            u[7]=Double.valueOf(measurement)*0.0833;

        }
        else {
            u[0]=Double.valueOf(measurement)*60*60*24*1000*365;
            u[1]=Double.valueOf(measurement)*60*60*24*365;
            u[2]=Double.valueOf(measurement)*60*24*365;
            u[3]=Double.valueOf(measurement)*24*365;
            u[4]=Double.valueOf(measurement)*365;
            u[5]=Double.valueOf(measurement)*52.177;
            u[6]=Double.valueOf(measurement)*12;
            u[7]=Double.valueOf(measurement)*1;
        }
        //end
        for(int i=0;i<=7;i++){
            if ((int) u[i] == u[i]) {
                int store = (int) u[i];
                a[i]=String.valueOf(store);
            }
            else {if((int) u[i]==0){
                a[i]=String.valueOf(u[i]);
            }else{
                BigDecimal bd = new BigDecimal(u[i]).setScale(2, RoundingMode.HALF_UP);
                u[i] = bd.doubleValue();
                a[i]=String.valueOf(u[i]);
            }
            }
        }
        if(!using) {
            using=true;
            units.add(0, a[0] + " ms");
            units.add(1, a[1] + " sec");
            units.add(2, a[2] + " min");
            units.add(3, a[3] + " hour");
            units.add(4, a[4] + " day");
            units.add(5, a[5] + " week");
            units.add(6, a[6] + " month");
            units.add(7, a[7] + " year");
        }
        else{
            units.set(0, a[0] + " ms");
            units.set(1, a[1] + " sec");
            units.set(2, a[2] + " min");
            units.set(3, a[3] + " hour");
            units.set(4, a[4] + " day");
            units.set(5, a[5] + " week");
            units.set(6, a[6] + " month");
            units.set(7, a[7] + " year");
        }
        adapter =new ArrayAdapter<String>(this,R.layout.align_right,units);
        lists.setAdapter(adapter);
    }

    public void temp(){

        if(choosen.contentEquals("C")){
            u[0]=Double.valueOf(measurement)*1;
            u[1]=Double.valueOf(measurement)*1.8+32;
            u[2]=Double.valueOf(measurement)+273.15;
        }
        else if(choosen.contentEquals("F")){
            u[0]=(Double.valueOf(measurement)-32)*5/9;
            u[1]=Double.valueOf(measurement)*1;
            u[2]=(Double.valueOf(measurement)-32)*5/9+273.15;

        }
        else{
            u[0]=Double.valueOf(measurement)-273.15;
            u[1]=(Double.valueOf(measurement)-273.15)*9/5+32;
            u[2]=Double.valueOf(measurement)*1;

        }

        for(int i=0;i<=2;i++){
            if ((int) u[i] == u[i]) {
                int store = (int) u[i];
                a[i]=String.valueOf(store);
            }
            else {
                if((int) u[i]==0){
                    a[i]=String.valueOf(u[i]);
                }else{
                    BigDecimal bd = new BigDecimal(u[i]).setScale(2, RoundingMode.HALF_UP);
                    u[i] = bd.doubleValue();
                    a[i]=String.valueOf(u[i]);
                }
            }
        }
        if(!using) {
            using=true;
            units.add(0, a[0] + " C");
            units.add(1, a[1] + " F");
            units.add(2, a[2] + " K");
        }
        else{
            units.set(0, a[0] + " C");
            units.set(1, a[1] + " F");
            units.set(2, a[2] + " K");
        }
        adapter =new ArrayAdapter<String>(this,R.layout.align_right,units);
        lists.setAdapter(adapter);
    }

    public void weight(){
        //start
        if(choosen.contentEquals("g")){
            u[0]=Double.valueOf(measurement)*1000;
            u[1]=Double.valueOf(measurement)*1;
            u[2]=Double.valueOf(measurement)*0.001;
            u[3]=Double.valueOf(measurement)*0.0022;
            u[4]=Double.valueOf(measurement)*11.6638;
        }
        else if(choosen.contentEquals("mg")){
            u[0]=Double.valueOf(measurement)*1;
            u[1]=Double.valueOf(measurement)*0.001;
            u[2]=Double.valueOf(measurement)*0.001*0.001;
            u[3]=Double.valueOf(measurement)*0.001*0.0022;
            u[4]=Double.valueOf(measurement)*11.6638*0.001;

        }
        else if(choosen.contentEquals("kg")){
            u[0]=Double.valueOf(measurement)*1000*1000;
            u[1]=Double.valueOf(measurement)*1000;
            u[2]=Double.valueOf(measurement)*1;
            u[3]=Double.valueOf(measurement)*0.0022*1000;
            u[4]=Double.valueOf(measurement)*11.6638*1000;

        }
        else if(choosen.contentEquals("pound")){
            u[0]=Double.valueOf(measurement)*1/0.0022*1000;
            u[1]=Double.valueOf(measurement)*1/0.0022;
            u[2]=Double.valueOf(measurement)*0.001*1/0.0022;
            u[3]=Double.valueOf(measurement)*1;
            u[4]=Double.valueOf(measurement)*1/0.0022*11.6638;

        }
        else {
            u[0]=Double.valueOf(measurement)*1/11.638*1000;
            u[1]=Double.valueOf(measurement)*1/11.638;
            u[2]=Double.valueOf(measurement)*1/11.638*0.001;
            u[3]=Double.valueOf(measurement)*1/11.638*0.0022;
            u[4]=Double.valueOf(measurement)*1;
        }
        //end
        for(int i=0;i<=4;i++){
            if ((int) u[i] == u[i]) {
                int store = (int) u[i];
                a[i]=String.valueOf(store);
            }
            else {
                if((int) u[i]==0){
                    a[i]=String.valueOf(u[i]);
                }else{
                    BigDecimal bd = new BigDecimal(u[i]).setScale(2, RoundingMode.HALF_UP);
                    u[i] = bd.doubleValue();
                    a[i]=String.valueOf(u[i]);
                }
            }
        }
        a[0]=String.valueOf(Double.valueOf(measurement)*u[0]);
        a[1]=String.valueOf(Double.valueOf(measurement)*u[1]);
        a[2]=String.valueOf(Double.valueOf(measurement)*u[2]);
        a[3]=String.valueOf(Double.valueOf(measurement)*u[3]);
        a[4]=String.valueOf(Double.valueOf(measurement)*u[4]);
        if(!using) {
            units.add(0, a[0] + " mg");
            units.add(1, a[1] + " g");
            units.add(2, a[2] + " kg");
            units.add(3, a[3] + " pound");
            units.add(4, a[4] + " tola");
            using=true;
        }
        else{

            units.set(0, a[0] + " mg");
            units.set(1, a[1] + " g");
            units.set(2, a[2] + " kg");
            units.set(3, a[3] + " pound");
            units.set(4, a[4] + " tola");
        }
        adapter =new ArrayAdapter<String>(this,R.layout.align_right,units);
        lists.setAdapter(adapter);
    }

    public void speed(){
        //start
        if(choosen.contentEquals("m/s")){
            u[0]=Double.valueOf(measurement)*2.237;
            u[1]=Double.valueOf(measurement)*1;
            u[2]=Double.valueOf(measurement)*3.281;
            u[3]=Double.valueOf(measurement)*0.001;
            u[4]=Double.valueOf(measurement)*1/60;
            u[5]=Double.valueOf(measurement)*196.85;
            u[6]=Double.valueOf(measurement)*0.06;
            u[7]=Double.valueOf(measurement)*3.6;
        }
        else if(choosen.contentEquals("mph")){
            u[0]=Double.valueOf(measurement)*1;
            u[1]=Double.valueOf(measurement)*1/2.237;
            u[2]=Double.valueOf(measurement)*1/2.237*3.281;
            u[3]=Double.valueOf(measurement)*0.001*1/2.237;
            u[4]=Double.valueOf(measurement)*1/(2.237*60);
            u[5]=Double.valueOf(measurement)*196.85*1/2.237;
            u[6]=Double.valueOf(measurement)*0.06*1/2.237;
            u[7]=Double.valueOf(measurement)*3.6*1/2.237;

        }
        else if(choosen.contentEquals("ft/s")){
            u[0]=Double.valueOf(measurement)*1/3.281*2.237;
            u[1]=Double.valueOf(measurement)*1/3.281;
            u[2]=Double.valueOf(measurement)*1;
            u[3]=Double.valueOf(measurement)*1/3.281*0.001;
            u[4]=Double.valueOf(measurement)*1/(3.281*60);
            u[5]=Double.valueOf(measurement)*1/3.281*196.85;
            u[6]=Double.valueOf(measurement)*0.06*1/3.281;
            u[7]=Double.valueOf(measurement)*1/3.281*3.6;

        }
        else if(choosen.contentEquals("km/s")){
            u[0]=Double.valueOf(measurement)*2.237*1000;
            u[1]=Double.valueOf(measurement)*1000;
            u[2]=Double.valueOf(measurement)*1000*3.281;
            u[3]=Double.valueOf(measurement)*1;
            u[4]=Double.valueOf(measurement)*1000/60;
            u[5]=Double.valueOf(measurement)*196.85*1000;
            u[6]=Double.valueOf(measurement)*0.006*1000;
            u[7]=Double.valueOf(measurement)*3.6*1000;

        }
        else if(choosen.contentEquals("m/min")){
            u[0]=Double.valueOf(measurement)*2.237*60;
            u[1]=Double.valueOf(measurement)*60;
            u[2]=Double.valueOf(measurement)*60*3.281;
            u[3]=Double.valueOf(measurement)*60*0.001;
            u[4]=Double.valueOf(measurement)*1;
            u[5]=Double.valueOf(measurement)*196.85*60;
            u[6]=Double.valueOf(measurement)*0.006*60;
            u[7]=Double.valueOf(measurement)*3.6*60;

        }
        else if(choosen.contentEquals("ft/min")){
            u[0]=Double.valueOf(measurement)*2.237/196.85;
            u[1]=Double.valueOf(measurement)*1/196.85;
            u[2]=Double.valueOf(measurement)*1/196.85*3.281;
            u[3]=Double.valueOf(measurement)*1/196.85*0.001;
            u[4]=Double.valueOf(measurement)*1/(60*196.85);
            u[5]=Double.valueOf(measurement)*1;
            u[6]=Double.valueOf(measurement)*0.006*1/196.85;
            u[7]=Double.valueOf(measurement)*3.6*1/196.85;

        }
        else if(choosen.contentEquals("km/min")){
            u[0]=Double.valueOf(measurement)*2.237/0.06;
            u[1]=Double.valueOf(measurement)*1/0.06;
            u[2]=Double.valueOf(measurement)*1/0.06*3.281;
            u[3]=Double.valueOf(measurement)*1/0.06*0.001;
            u[4]=Double.valueOf(measurement)*1/(60*0.06);
            u[5]=Double.valueOf(measurement)*196.5/0.06;
            u[6]=Double.valueOf(measurement)*1;
            u[7]=Double.valueOf(measurement)*3.6*1/196.85;

        }
        else {
            u[0]=Double.valueOf(measurement)*2.237/3.6;
            u[1]=Double.valueOf(measurement)*1/3.6;
            u[2]=Double.valueOf(measurement)*1/3.6*3.281;
            u[3]=Double.valueOf(measurement)*1/3.6*0.001;
            u[4]=Double.valueOf(measurement)*1/(60*3.6);
            u[5]=Double.valueOf(measurement)*196.85/3.6;
            u[6]=Double.valueOf(measurement)*0.006*1/3.6;
            u[7]=Double.valueOf(measurement)*1;
        }
        //end
        for(int i=0;i<=7;i++){
            if ((int) u[i] == u[i]) {
                int store = (int) u[i];
                a[i]=String.valueOf(store);
            }
            else {
                if((int) u[i]==0){
                    a[i]=String.valueOf(u[i]);
                }else{
                    BigDecimal bd = new BigDecimal(u[i]).setScale(2, RoundingMode.HALF_UP);
                    u[i] = bd.doubleValue();
                    a[i]=String.valueOf(u[i]);
                }
            }
        }
        if(!using) {
            using = true;
            units.add(0, a[0] + " mph");
            units.add(1, a[1] + " m/s");
            units.add(2, a[2] + " ft/s");
            units.add(3, a[3] + " km/s");
            units.add(4, a[4] + " m/min");
            units.add(5, a[5] + " ft/min");
            units.add(6, a[6] + " km/min");
            units.add(7, a[7] + " km/hr");
        }
        else{
            units.set(0,a[0]+" mph");
            units.set(1,a[1]+" m/s");
            units.set(2,a[2]+" ft/s");
            units.set(3,a[3]+" km/s");
            units.set(4,a[4]+" m/min");
            units.set(5,a[5]+" ft/min");
            units.set(6,a[6]+" km/min");
            units.set(7,a[7]+" km/hr");

        }
        adapter =new ArrayAdapter<String>(this,R.layout.align_right,units);
        lists.setAdapter(adapter);
    }

    public void file(){
        //start
        if(choosen.contentEquals("bit")){
            u[0]=Double.valueOf(measurement)*1;
            u[1]=Double.valueOf(measurement)*1/8;
            u[2]=Double.valueOf(measurement)*0.001/8;
            u[3]=Double.valueOf(measurement)*0.001*0.001/8;
            u[4]=Double.valueOf(measurement)*0.001*0.001*0.001/8;
        }
        else if(choosen.contentEquals("Byte")){
            u[0]=Double.valueOf(measurement)*8;
            u[1]=Double.valueOf(measurement)*1;
            u[2]=Double.valueOf(measurement)*0.001;
            u[3]=Double.valueOf(measurement)*0.001*0.001;
            u[4]=Double.valueOf(measurement)*0.001*0.001*0.001;
        }
        else if(choosen.contentEquals("KB")){
            u[0]=Double.valueOf(measurement)*8*1000;
            u[1]=Double.valueOf(measurement)*1000;
            u[2]=Double.valueOf(measurement)*1;
            u[3]=Double.valueOf(measurement)*0.001;
            u[4]=Double.valueOf(measurement)*0.001*0.001;
        }
        else if(choosen.contentEquals("MB")){
            u[0]=Double.valueOf(measurement)*8*1000*1000;
            u[1]=Double.valueOf(measurement)*1000*1000;
            u[2]=Double.valueOf(measurement)*1000;
            u[3]=Double.valueOf(measurement)*1;
            u[4]=Double.valueOf(measurement)*0.001;
        }
        else if(choosen.contentEquals("GB")){
            u[0]=Double.valueOf(measurement)*8*1000*1000*1000;
            u[1]=Double.valueOf(measurement)*1000*1000*1000;
            u[2]=Double.valueOf(measurement)*1000*1000;
            u[3]=Double.valueOf(measurement)*1000;
            u[4]=Double.valueOf(measurement)*1;
        }
        //end
        for(int i=0;i<=4;i++){
            if ((int) u[i] == u[i]) {
                int store = (int) u[i];
                a[i]=String.valueOf(store);
            }
            else {
                if((int) u[i]==0){
                    a[i]=String.valueOf(u[i]);
                }else{
                    BigDecimal bd = new BigDecimal(u[i]).setScale(2, RoundingMode.HALF_UP);
                    u[i] = bd.doubleValue();
                    a[i]=String.valueOf(u[i]);
                }            }
        }
        if(!using) {
            using = true;

            units.add(0, a[0] + " bit");
            units.add(1, a[1] + " Byte");
            units.add(2, a[2] + " kB");
            units.add(3, a[3] + " MB");
            units.add(4, a[4] + " GB");
        }
        else{
            units.set(0,a[0]+" bit");
            units.set(1,a[1]+" Byte");
            units.set(2,a[2]+" kB");
            units.set(3,a[3]+" MB");
            units.set(4,a[4]+" GB");
        }
        adapter =new ArrayAdapter<String>(this,R.layout.align_right,units);
        lists.setAdapter(adapter);
    }

    public void angle(){
        //start
        if(choosen.contentEquals("deg")){
            u[0]=Double.valueOf(measurement)*0.0175;
            u[1]=Double.valueOf(measurement)*1;
            u[2]=Double.valueOf(measurement)*0.016667;
            u[3]=Double.valueOf(measurement)*0.016667*60;
            u[4]=Double.valueOf(measurement)*1.111;
        }
        else if(choosen.contentEquals("rad")){
            u[0]=Double.valueOf(measurement)*1;
            u[1]=Double.valueOf(measurement)*1/0.0175;
            u[2]=Double.valueOf(measurement)*0.016667/0.0175;
            u[3]=Double.valueOf(measurement)*0.016667/0.0175*60;
            u[4]=Double.valueOf(measurement)*1.111/0.0175;

        }
        else if(choosen.contentEquals("min")){
            u[0]=Double.valueOf(measurement)*0.0175/0.016667;
            u[1]=Double.valueOf(measurement)*1/0.016667;
            u[2]=Double.valueOf(measurement)*1;
            u[3]=Double.valueOf(measurement)*60;
            u[4]=Double.valueOf(measurement)*1.111/0.016667;

        }
        else if(choosen.contentEquals("sec")){
            u[0]=Double.valueOf(measurement)*0.0175/(0.016667*60);
            u[1]=Double.valueOf(measurement)*1/(0.016667*60);
            u[2]=Double.valueOf(measurement)*1/60;
            u[3]=Double.valueOf(measurement)*1;
            u[4]=Double.valueOf(measurement)*1.111/(0.016667*60);

        }
        else{
            u[0]=Double.valueOf(measurement)*0.0175/1.1111;
            u[1]=Double.valueOf(measurement)*1/1.111;
            u[2]=Double.valueOf(measurement)*0.016667/1.111;
            u[3]=Double.valueOf(measurement)*60*0.016667/1.111;
            u[4]=Double.valueOf(measurement)*1;

        }
        //big end
        for(int i=0;i<=4;i++){
            if ((int) u[i] == u[i]) {
                int store = (int) u[i];
                a[i]=String.valueOf(store);
            }
            else {
                if((int) u[i]==0){
                    a[i]=String.valueOf(u[i]);
                }else{
                    BigDecimal bd = new BigDecimal(u[i]).setScale(2, RoundingMode.HALF_UP);
                    u[i] = bd.doubleValue();
                    a[i]=String.valueOf(u[i]);
                }
            }
        }
        if(!using) {
            using = true;
            units.add(0, a[0] + " rad");
            units.add(1, a[1] + " deg");
            units.add(2, a[2] + " min");
            units.add(3, a[3] + " sec");
            units.add(4, a[4] + " grad");
        }
        else{
            units.set(0,a[0]+" rad");
            units.set(1,a[1]+" deg");
            units.set(2,a[2]+" min");
            units.set(3,a[3]+" sec");
            units.set(4,a[4]+" grad");
        }
        adapter =new ArrayAdapter<String>(this,R.layout.align_right,units);
        lists.setAdapter(adapter);
    }

    public void set(){
        switch(name){
            case "Length":
                length();
                break;
            case "Area":
                area();
                break;
            case "Temperature":
                temp();
                break;
            case "Time":
                clock();
                break;
            case "Speed":
                speed();
                break;
            case "Weight":
                weight();
                break;
            case "Angle":
                angle();
                break;
            case "Data":
                file();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_demo);
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
        Intent intent= getIntent();
        name =intent.getStringExtra("name");
        lists =  findViewById(R.id.list1);
        adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,a);
        lists.setAdapter(adapter);
        digits=findViewById(R.id.digit);
        TextView text=findViewById(R.id.text);
        text.setText(name);
        spiner =(Spinner)findViewById(R.id.spinner);
        switch(name) {
            case "Length":
                spinner.add("mm");
                spinner.add("cm");
                spinner.add("m");
                spinner.add("km");
                spinner.add("inch");
                spinner.add("ft");
                spinner.add("mile");
                spinner.add("yard");
                length();
                break;
            case "Area":
                spinner.add("mm\u00B2");
                spinner.add("cm\u00B2");
                spinner.add("m\u00B2");
                spinner.add("km\u00B2");
                spinner.add("inch\u00B2");
                spinner.add("ft\u00B2");
                spinner.add("acre");
                spinner.add("yard\u00B2");
                area();
                break;
            case "Temperature":
                spinner.add("C");
                spinner.add("F");
                spinner.add("K");
                temp();
                break;
            case "Time":
                spinner.add("ms");
                spinner.add("sec");
                spinner.add("min");
                spinner.add("hour");
                spinner.add("day");
                spinner.add("week");
                spinner.add("month");
                spinner.add("year");
                clock();
                break;
            case "Speed":
                spinner.add("mph");
                spinner.add("m/s");
                spinner.add("ft/s");
                spinner.add("km/s");
                spinner.add("m/min");
                spinner.add("ft/min");
                spinner.add("km/min");
                spinner.add("km/hour");
                speed();
                break;
            case "Weight":
                spinner.add("mg");
                spinner.add("g");
                spinner.add("kg");
                spinner.add("pound");
                spinner.add("tola");
                weight();
                break;
            case "Angle":
                spinner.add("rad");
                spinner.add("deg");
                spinner.add("min");
                spinner.add("sec");
                spinner.add("grad");
                angle();
                break;
            case "Data":
                spinner.add("bit");
                spinner.add("Byte");
                spinner.add("KB");
                spinner.add("MB");
                spinner.add("GB");
                file();
                break;
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinner);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(dataAdapter);
        spiner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        choosen =adapterView.getItemAtPosition(i).toString();
        set();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                demo.super.onBackPressed();
            }
        });

    }
}
