package com.home.calsi;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    String number="";
    String total="";
    String [] terms=new String[100];
    String [] copy=new String[100];
    int t=0;
    String digit="";
    double store=0;
    boolean error=false;
    String errormsg="";
    TextView text;
    TextView ans;
    int deg=1;
    int braces=0;
    ListView listView;
    LinearLayout list;
    ArrayAdapter arrayAdapter;
//digit pressed

    public void click(View view){
        digit = view.getTag().toString();
        ans.setTextColor(Color.parseColor("#504F4F"));
        ans.setText(String.valueOf(""));
        char l= ' ';
        if(!(total.isEmpty())) {
            l = total.charAt(total.length() - 1);
        }
        if(!(total.isEmpty()) && (l != '*')&& (l != '(')&& (l != '^') && (l != '/') && (l != '-') && (l!='+')&& digit.contentEquals("-")){
            t++;
            terms[t]="+";
            number="";
            t++;
        }
        // h,R,G,F,g,k,m
        else if(l==')'||l=='e'||l=='h'||l=='R'||l=='G'||l=='F'||l=='g'||l=='k'||l=='m'||l=='%'||l=='!'){
            t++;
            terms[t]="*";
            t++;
        }
        if(l=='^'){
            t++;
            if(braces==0){
                error=false;
                errormsg="";
            }
        }
        if((number.isEmpty()&&digit.contentEquals("."))||(number.contentEquals("-")&&digit.contentEquals("."))){
            number="0";
        }
        number += digit;
        terms[t]=number;
        if(digit.charAt(0)!='-') {
            try {
                double value=Double.parseDouble(number);
            } catch (NumberFormatException e) {
                error = true;
                errormsg="Bad expression";
            }
            if(t>0){
                for(int i=0;i<t+1;i++){
                    copy[i]=terms[i];
                }
                calculation(t+1);
            }
        }
        total +=digit;
        text.setText(String.valueOf(total));
    }

//process pressed

    public void solve(View view){
        String a = view.getTag().toString();
        if(!total.isEmpty()){
            char l = total.charAt(total.length()-1);
            //last update l!='!'&&l!='%'&&
            if(l!='^'&&l!='(') {
                if (l != '*' && l != '/' && l != '+' && l != '-') {
                    t++;
                    terms[t] = a;
                    total += a;
                    text.setText(total);
                    number = "";
                    t++;
                } else {
                    if (l != '-') {
                        t--;
                        terms[t] = a;
                        total = total.substring(0, total.length() - 1);
                        total += a;
                        text.setText(total);
                        t++;
                    }
                }
            }
        }
    }

    public String[] reverse(int s,int m,String a[],int l){
        m++;
        for(int r=s;m<l;r++){
            a[r]=a[m];
            m++;
        }
        return a;
    }

    public void calculation(int dl){
        String [] copydigit=new String[100];
        for(int i=0;i<dl;i++){
            copydigit[i]=copy[i];
        }
        if(!error) {
            //for braces

            for (int z = 0; z < dl; z++) {
                if (copydigit[z].contentEquals("(")) {
                    for (int y = z + 1; y < dl; y++) {
                        if (copydigit[y].contentEquals(")")) {
                            int ip = 0;
                            for (int xc = z + 1; xc < y; xc++) {
                                copy[ip] = copydigit[xc];
                                ip++;
                            }
                            calculation(y - (z + 1));
                            copydigit = reverse(z + 1, y, copydigit, dl);
                            copydigit[z] = String.valueOf(store);
                            if(z!=0){
                                if(copydigit[z-1].contentEquals("-")){
                                    copydigit[z]=String.valueOf(-Double.valueOf(copydigit[z]));
                                }
                            }
                            dl = dl - (y - z);
                            break;
                        }
                    }
                }
            }

            for(int z=0;z<dl;z++) {
                if (copydigit[z].contentEquals("%")) {
                    copydigit[z - 1] = String.valueOf(Double.parseDouble(copydigit[z - 1]) * 0.01);
                    copydigit = reverse(z,z, copydigit, dl);
                    dl -= 1;
                    z--;
                }
                else if(copydigit[z].contentEquals("^")) {
                    copydigit[z - 1] = String.valueOf(Math.pow(Double.parseDouble(copydigit[z - 1]),Double.parseDouble(copydigit[z + 1])));
                    copydigit = reverse(z,z+1, copydigit, dl);

                    dl -= 2;
                    z--;
                }
                else if(copydigit[z].contentEquals("!")){
                    int num=Integer.valueOf(copydigit[z-1]);
                    if(num==Double.valueOf(copydigit[z-1])){
                        int numerical=Integer.valueOf(copydigit[z-1]);
                        if(numerical<0){
                            numerical*=-1;
                        }
                        double fact=1;
                        for(int i=1;i<=numerical;i++){
                            fact=fact*i;
                        }
                        copydigit[z-1]=String.valueOf(fact);
                        copydigit = reverse(z,z, copydigit, dl);
                        dl -= 1;
                        z--;
                    }
                    else{
                        errormsg="Factorial error";
                        error=true;
                    }
                }
            }
        }

        //sin cos sin- cos- tan- sinh cosh tanh

        if(!error){
            for (int z = 0; z < dl; z++) {
                if (copydigit[z].contentEquals("sin")) {
                    if(deg==0){
                        copydigit[z] = String.valueOf(Math.sin(Double.valueOf(copydigit[z+1])));
                    }
                    else{
                        copydigit[z] = String.valueOf(Math.sin(Math.toRadians(Double.valueOf(copydigit[z+1]))));
                    }
                    copydigit = reverse(z+1, z+1, copydigit, dl);
                    if(z!=0){
                        if(copydigit[z-1].contentEquals("-")){
                            copydigit[z]=String.valueOf(-Double.valueOf(copydigit[z]));
                        }
                    }
                    dl -=1;
                    z--;

                }else if(copydigit[z].contentEquals("cos")){
                    if(deg==0){
                        copydigit[z] = String.valueOf(Math.cos(Double.valueOf(copydigit[z+1])));
                    }
                    else{
                        copydigit[z] = String.valueOf(Math.cos(Math.toRadians(Double.valueOf(copydigit[z+1]))));
                    }
                    copydigit = reverse(z+1, z+1, copydigit, dl);
                    if(z!=0){
                        if(copydigit[z-1].contentEquals("-")){
                            copydigit[z]=String.valueOf(-Double.valueOf(copydigit[z]));
                        }
                    }
                    dl -=1;
                    z--;
                }
                else if (copydigit[z].contentEquals("tan-1")) {
                    if(deg==0){
                        copydigit[z] = String.valueOf(Math.atan(Double.valueOf(copydigit[z+1])));
                    }
                    else{
                        copydigit[z] = String.valueOf(Math.atan(Double.valueOf(copydigit[z + 1])));
                        copydigit[z]=String.valueOf(Math.toDegrees(Double.valueOf(copydigit[z])));
                    }
                    copydigit = reverse(z+1, z+1, copydigit, dl);
                    if(z!=0){
                        if(copydigit[z-1].contentEquals("-")){
                            copydigit[z]=String.valueOf(-Double.valueOf(copydigit[z]));
                        }
                    }
                    dl -=1;
                    z--;

                }
            }
        }


        if(!error) {
            for (int z = 0; z < dl; z++) {
                if (copydigit[z].contentEquals("tan")) {
                        if (deg == 0) {
                            copydigit[z] = String.valueOf(Math.tan(Double.valueOf(copydigit[z + 1])));
                        } else {
                            copydigit[z] = String.valueOf(Math.tan(Math.toRadians(Double.valueOf(copydigit[z + 1]))));
                        }
                        copydigit = reverse(z + 1, z + 1, copydigit, dl);
                    if(z!=0){
                        if(copydigit[z-1].contentEquals("-")){
                            copydigit[z]=String.valueOf(-Double.valueOf(copydigit[z]));
                        }
                    }
                    dl -= 1;
                        z--;
                }
            }
        }
        if(!error){
            for (int z = 0; z < dl; z++) {
                if (copydigit[z].contentEquals("sin-1")) {
                    if((Double.valueOf(copydigit[z + 1])>=-1)&&(Double.valueOf(copydigit[z + 1])<=1)) {
                        if (deg == 0) {
                            copydigit[z] = String.valueOf(Math.asin(Double.valueOf(copydigit[z + 1])));
                        } else {
                            copydigit[z] = String.valueOf(Math.asin(Double.valueOf(copydigit[z + 1])));
                            copydigit[z]=String.valueOf(Math.toDegrees(Double.valueOf(copydigit[z])));
                        }
                        copydigit = reverse(z + 1, z + 1, copydigit, dl);
                        if(z!=0){
                            if(copydigit[z-1].contentEquals("-")){
                                copydigit[z]=String.valueOf(-Double.valueOf(copydigit[z]));
                            }
                        }
                        dl -= 1;
                        z--;
                    }else{
                        errormsg="domain error";
                        error=true;
                    }
                }
            }
        }
        if(!error){
            for (int z = 0; z < dl; z++) {
                if (copydigit[z].contentEquals("cos-1")) {
                    if((Double.valueOf(copydigit[z + 1])>=-1)&&(Double.valueOf(copydigit[z + 1])<=1)) {
                        if (deg == 0) {
                            copydigit[z] = String.valueOf(Math.acos(Double.valueOf(copydigit[z + 1])));
                        } else {
                            copydigit[z] = String.valueOf(Math.acos(Double.valueOf(copydigit[z + 1])));
                            copydigit[z]=String.valueOf(Math.toDegrees(Double.valueOf(copydigit[z])));
                        }
                        copydigit = reverse(z + 1, z + 1, copydigit, dl);
                        if(z!=0){
                            if(copydigit[z-1].contentEquals("-")){
                                copydigit[z]=String.valueOf(-Double.valueOf(copydigit[z]));
                            }
                        }
                        dl -= 1;
                        z--;
                    }
                    else{
                        errormsg="domain error";
                        error=true;
                    }

                }
            }
        }

        if(!error) {
            for (int z = 0; z < dl; z++) {
                if (copydigit[z].contentEquals("ln")) {
                    if(Double.valueOf(copydigit[z+1])>0){
                        copydigit[z] = String.valueOf(Math.log(Double.valueOf(copydigit[z+1])));
                        copydigit = reverse(z+1, z+1, copydigit, dl);
                        if(z!=0){
                            if(copydigit[z-1].contentEquals("-")){
                                copydigit[z]=String.valueOf(-Double.valueOf(copydigit[z]));
                            }
                        }
                        dl -=1;
                        z--;
                    }
                    else{
                        error=true;
                        errormsg="domain error";
                    }
                }
            }
        }
        if(!error) {
            for (int z = 0; z < dl; z++) {
                if (copydigit[z].contentEquals("sqrt")) {
                    if(Double.valueOf(copydigit[z+1])>0){
                        copydigit[z] = String.valueOf(Math.sqrt(Double.valueOf(copydigit[z+1])));
                        copydigit = reverse(z+1, z+1, copydigit, dl);
                        if(z!=0){
                            if(copydigit[z-1].contentEquals("-")){
                                copydigit[z]=String.valueOf(-Double.valueOf(copydigit[z]));
                            }
                        }
                        dl -=1;
                        z--;
                    }
                    else{
                        error=true;
                        errormsg="domain error";
                    }
                }
            }
        }

        if(!error){
            for (int z = 0; z < dl; z++) {
                if (copydigit[z].contentEquals("log")) {
                    if(Double.valueOf(copydigit[z+1])>0){
                        copydigit[z] = String.valueOf(Math.log10(Double.valueOf(copydigit[z+1])));
                        copydigit = reverse(z+1, z+1, copydigit, dl);
                        if(z!=0){
                            if(copydigit[z-1].contentEquals("-")){
                                copydigit[z]=String.valueOf(-Double.valueOf(copydigit[z]));
                            }
                        }
                        dl -=1;
                        z--;
                    }
                    else{
                        error=true;
                        errormsg="domain error";
                    }
                }
            }
        }

        if(!error){
            //for multiplication division
            for(int z=0;z<dl;z++){
                if(copydigit[z].contentEquals("*")){
                    copydigit[z-1]=String.valueOf(Double.parseDouble(copydigit[z-1]) * Double.parseDouble(copydigit[z+1]));
                    copydigit= reverse(z,z+1,copydigit,dl);
                    dl-=2;
                    z--;
                }
                else if(copydigit[z].contentEquals("/")){
                    if(copydigit[z+1].contentEquals("0")){
                        error=true;
                        errormsg="Not defined";
                        break;
                    }
                    else{
                        copydigit[z-1]=String.valueOf(Double.parseDouble(copydigit[z-1]) / Double.parseDouble(copydigit[z+1]));
                        copydigit= reverse(z,z+1,copydigit,dl);
                        dl-=2;
                        z--;
                    }
                }
            }
        }
        if(!error){
            //for addition
            for(int z=0;z<dl;z++){
                if(copydigit[z].contentEquals("+")){
                    copydigit[z-1]=String.valueOf(Double.parseDouble(copydigit[z-1]) + Double.parseDouble(copydigit[z+1]));
                    copydigit= reverse(z,z+1,copydigit,dl);
                    dl-=2;
                    z--;
                }
            }
            store = Double.parseDouble(copydigit[0]);
            if ((int) store == store) {
                int store = (int) this.store;
                ans.setText(String.valueOf(store));
            } else {
                if((int) store==0){
                    ans.setText(String.valueOf(store));
                }
                else{
                BigDecimal bd = new BigDecimal(store).setScale(12, RoundingMode.HALF_UP);
                store = bd.doubleValue();
                ans.setText(String.valueOf(store));}
            }
        }
    }

    public void equal(View view){
        if(!(error)) {
            if (!total.isEmpty()) {
                if(t>0) {
                    if ((int) store == store) {
                        int store = (int) this.store;
                        text.setText(String.valueOf(store));
                        number = Integer.toString(store);
                        total = Integer.toString(store);
                    }
                    else {
                        number = Double.toString(store);
                        text.setText(String.valueOf(store));
                        total = String.valueOf(store);
                    }
                    ans.setText(String.valueOf(""));
                    for(int b=0;b<t+1;b++){
                        terms[b]="n";
                    }
                    t = 0;
                    terms[t] = number;
                }
            }
        }
        else{
            ans.setTextColor(Color.parseColor("#ffcc0000"));
            ans.setText(String.valueOf(errormsg));
        }
    }

    public void clear(){
        if(!total.isEmpty()){
            char l = total.charAt(total.length() - 1);
            // h,R,G,F,g,k,m
            if(l!= 'e' && l != ')' && l != '('&& l != 'h' && l != 'R' && l != 'G' && l !='F' &&l != 'g' && l != 'k' && l != 'm' ) {
                if (l != '*' && l != '+' && l != '/' &&l != '%'&&l != '^'&&l != '!' && (l != '-' || total.length() == 1)) {
                    if (terms[t].length() >= 2) {
                        terms[t] = terms[t].substring(0, terms[t].length() - 1);
                        number = number.substring(0, number.length() - 1);
                        total=total.substring(0,total.length()-1);
                        if (terms[t].charAt(terms[t].length() - 1) != '-') {
                            if (t > 0) {
                                for (int i = 0; i < t + 1; i++) {
                                    copy[i] = terms[i];
                                }
                                calculation(t + 1);
                            }
                        } else {
                            ans.setText("");
                        }
                    } else {
                        total=total.substring(0,total.length()-1);
                        terms[t] = "n";
                        if(t!=0){
                            String la=terms[t-1];
                            if(la.contentEquals("e")||la.contentEquals(")")||la.contentEquals("h")||la.contentEquals("R")
                                    ||la.contentEquals("G")||la.contentEquals("F")||la.contentEquals("g")||la.contentEquals("k")||la.contentEquals("atm")){
                                t--;
                                terms[t]="n";
                                t--;
                            }
                            else if(la.contentEquals("^")){
                                error=true;
                                errormsg="Domain error";
                            }
                        }
                        number = "";
                        ans.setText("");
                    }
                } else {
                    if(l=='^'){
                        error=false;
                        errormsg="";
                    }
                    if (t >= 2) {
                        t--;
                        terms[t] = "n";
                        t--;
                    }
                    number = terms[t];
                    total=total.substring(0,total.length()-1);
                    if (t > 0) {
                        for (int i = 0; i < t + 1; i++) {
                            copy[i] = terms[i];
                        }
                        calculation(t + 1);
                    }
                }
            }
            else if (l == '(') {
                error=false;
                errormsg="";
                t--;
                braces--;
                total=total.substring(0,total.length()-1);
                terms[t]="n";
                if(t!=0) {
                    t--;
                    if (terms[t].contentEquals("sin") || terms[t].contentEquals("log") || terms[t].contentEquals("ln") || terms[t].contentEquals("sqrt")
                            || terms[t].contentEquals("cos") || terms[t].contentEquals("tan") || terms[t].contentEquals("sin-1") || terms[t].contentEquals("cos-1") || terms[t].contentEquals("tan-1")) {
                        if (terms[t].contentEquals("sqrt")) {
                            total = total.substring(0, total.length() - 4);
                        } else if (terms[t].contentEquals("ln")) {
                            total = total.substring(0, total.length() - 2);
                        } else if (terms[t].contentEquals("tan-1") || terms[t].contentEquals("cos-1") || terms[t].contentEquals("sin-1")) {
                            total = total.substring(0, total.length() - 5);
                        } else {
                            total = total.substring(0, total.length() - 3);
                        }
                        terms[t] = "n";
                        //
                        if (t != 0) {
                            t--;
                        }
                        //+/%^!-
                    }
                    char last = ' ';
                    if (!total.isEmpty()) {
                        last = total.charAt(total.length() - 1);
                    }
                    //*+/^%!-
                    if (last == '*' || last == '+' || last == '/' || last == '^' || last == '%' || last == '!' || last == '-') {
                        t++;
                    }else if (t >= 2) {
                        t--;
                        terms[t] = "n";
                        t--;
                        number = terms[t];
                    }
                }
            }
            else if (l == ')') {
                error=true;
                braces++;
                errormsg="Inappropriate braces";
                total = total.substring(0, total.length() - 1);
                terms[t]="n";
                t--;
                number=terms[t];
            }
            // h,R,G,F,g,k,m-101325
            else if(l=='e'||l=='R'||l=='G'||l=='F'||l=='g'||l=='k'||l=='m'||l=='h'){
                if(terms[t].contentEquals("3.141592653589")||terms[t].contentEquals("-3.141592653589")
                        ||terms[t].contentEquals("-101325")||terms[t].contentEquals("101325")){
                    total = total.substring(0, total.length() - 3);
                }else{
                    total = total.substring(0, total.length() - 1);
                }
                terms[t]="n";

                char last=' ';
                if(!total.isEmpty()) {
                    last = total.charAt(total.length() - 1);
                }
                //*+-(^
                if(last=='*'|| last=='+'||last=='/' ||last=='^'||last=='(' || last=='-') {
                    t--;
                    terms[t] = "n";
                    t--;
                    number=terms[t];
                }
            }
            text.setText(total);
            if(total.isEmpty()){
                ans.setText("");
            }
        }
        else{
            text.setText("");
            ans.setText("");
            error=false;
            braces=0;
            errormsg="";
        }
    }

    public void allclear(View view){
        longclick();
    }

    public void longclick(){
        text.setText(String.valueOf(""));
        ans.setText(String.valueOf(""));
        for(int b=0;b<t+1;b++){
            terms[b]="n";
        }
        number="";
        total="";
        braces=0;
        error=false;
        store = 0 ;

        t=0;
    }

    public void listing(View view){
        list.setVisibility(View.VISIBLE);
        if(view.getTag().toString().contentEquals("trigo")){
            TextView extra =findViewById(R.id.extra);
            extra.setText("TRIGNOMETRY");
            String [] a={"sin","cos","tan","sin-1","cos-1","tan-1"};
            arrayAdapter=new ArrayAdapter<String>(this,R.layout.align_right,a);
            listView.setAdapter(arrayAdapter);
        }
        else if(view.getTag().toString().contentEquals("con")){
            String [] a={"e","pie","h","R","G","F","g","k","atm"};
            TextView extra =findViewById(R.id.extra);
            extra.setText("CONSTANTS");
            arrayAdapter=new ArrayAdapter<String>(this,R.layout.align_right,a);
            listView.setAdapter(arrayAdapter);
        }
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    public void degree(View view){
        Button degree = findViewById(R.id.degButton);
        if(deg==1) {
            deg=0;
            degree.setText("RAD");
        }
        else{
            deg=1;
            degree.setText("DEG");
        }
        if(t>0&&!total.isEmpty()){
            for(int i=0;i<t+1;i++){
                copy[i]=terms[i];
            }
            calculation(t+1);
        }  }

    public void cancellist(View view){
        list.setVisibility(View.INVISIBLE);
    }

    public void advanced(View view){
        ans.setTextColor(Color.parseColor("#504F4F"));
        String data =view.getTag().toString();
        char l = ' ';
        if(!total.isEmpty()){
            l = total.charAt(total.length()-1);
        }

        //braces
        if(data.contentEquals("()")){
            if(braces==0) {
                if (!total.isEmpty() && l != '*' && l != '/' && l != '-' && l != '+') {
                    t++;
                    terms[t] = "*";
                    t++;
                }
                terms[t]="(";
                total+="(";
                braces++;
                t++;
                number="";
                error=true;
                errormsg="Inappropriate braces";
            }
            else{
                if (l!='(' && l != '*' && l != '/' && l != '-' && l != '+') {
                    error=false;
                    errormsg="";
                    t++;
                    number="";
                    terms[t] = ")";
                    total += ")";
                    braces--;
                    for (int i = 0; i < t + 1; i++) {
                        copy[i] = terms[i];
                    }
                    calculation(t + 1);
                }
            }
        }
        //% !
        else if(data.contentEquals("%")||data.contentEquals("!")||data.contentEquals("^")){
            if(!total.isEmpty()&&l!='*'&&l!='/'&&l!='-'&&l!='+') {
                t++;
                terms[t]=data;
                total += data;
                number = "";
                if(braces==0&&!data.contentEquals("^")) {
                    for (int i = 0; i < t + 1; i++) {
                        copy[i] = terms[i];
                    }
                    calculation(t + 1);
                }

            }
        }


        else if(data.contentEquals("ln")||data.contentEquals("log")||data.contentEquals("sqrt")){
            if(braces!=0){
                if( l != '*' && l != '/' && l != '-' && l != '+') {
                }
                else {
                    terms[t]="0";
                    t++;
                    total+="0";
                }
                terms[t] = ")";
                total+= ")";
                braces--;
                t++;
            }
            else {
                if (!total.isEmpty() && l != '*' && l != '/' && l != '-' && l != '+') {
                    t++;
                    terms[t] = "*";
                    number = "";
                    t++;
                }
            }
            number="";
            terms[t]=data;
            total+=data+"(";
            braces++;
            t++;
            terms[t]="(";
            t++;
            error=true;
            errormsg="Inappropriate braces";
        }
        text.setText(total);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        navigationView.setCheckedItem(R.id.simplecalculate);
        text = findViewById(R.id.solveTextView);
        ans =findViewById(R.id.ansTextView);

        terms[0]="n";
        Button button=(Button)findViewById(R.id.cancelButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longclick();
                return true;
            }
        });
        listView=findViewById(R.id.list);
        list=findViewById(R.id.listLayot);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data=parent.getItemAtPosition(position).toString();
                ans.setTextColor(Color.parseColor("#504F4F"));
                char l = ' ';
                if(!total.isEmpty()){
                    l = total.charAt(total.length()-1);
                }
                if(data.contentEquals("sin")||data.contentEquals("cos")||data.contentEquals("tan")||data.contentEquals("sin-1")||data.contentEquals("cos-1")||data.contentEquals("tan-1")){
                    if(braces!=0){
                        if( l != '*' && l != '/' && l != '-' && l != '+') {
                        }
                        else {
                            terms[t]="0";
                            t++;
                            total+="0";
                        }
                        terms[t] = ")";
                        total+= ")";
                        braces--;
                        t++;
                    }
                    else {
                        if (!total.isEmpty() && l != '*' && l != '/' && l != '-' && l != '+') {
                            t++;
                            terms[t] = "*";
                            number = "";
                            t++;
                        }
                    }
                    number="";
                    terms[t]=data;
                    total+=data+"(";
                    braces++;
                    t++;
                    terms[t]="(";
                    t++;
                    error=true;
                    errormsg="Inappropriate braces";
                }

                else if(data.contentEquals("e")||data.contentEquals("pie")||data.contentEquals("h")||data.contentEquals("R")||data.contentEquals("G")||data.contentEquals("F")||data.contentEquals("g")||data.contentEquals("k")||data.contentEquals("atm")){
                    if(!total.isEmpty()&&l!='*'&&l!='/'&&l!='-'&&l!='+'&&l!='('){
                        t++;
                        terms[t]="*";
                        t++;
                    }
                    number="";
                    if(l=='-'){
                        if (data.contentEquals("h")) {
                            terms[t] = "-6.62606E-34";
                        }else if(data.contentEquals("e")){
                            terms[t]="-2.718281828459";
                        }
                        else if(data.contentEquals("pie")){
                            terms[t]="-3.141592653589";
                        }
                        else if (data.contentEquals("R")) {
                            terms[t] = "-8.314472";
                        } else if (data.contentEquals("G")) {
                            terms[t] = ("-6.62606E-11");
                        } else if (data.contentEquals("F")) {
                            terms[t] = "-96485.3399";
                        } else if (data.contentEquals("g")) {
                            terms[t] = "-9.81";
                        } else if (data.contentEquals("k")) {
                            terms[t] = ("-1.3806E-23");
                        } else if (data.contentEquals("atm")) {
                            terms[t] = "-101325";
                        }
                    }
                    else {
                        if (data.contentEquals("h")) {
                            terms[t] = ("6.62606E-34");
                        }else if(data.contentEquals("e")){
                            terms[t]="2.718281828459";
                        }
                        else if(data.contentEquals("pie")){
                            terms[t]="3.141592653589";
                        }
                        else if (data.contentEquals("R")) {
                            terms[t] = "8.314472";
                        } else if (data.contentEquals("G")) {
                            terms[t] = ("6.62606E-11");
                        } else if (data.contentEquals("F")) {
                            terms[t] = "96485.3399";
                        } else if (data.contentEquals("g")) {
                            terms[t] = "9.81";
                        } else if (data.contentEquals("k")) {
                            terms[t] = ("1.3806E-23");
                        } else if (data.contentEquals("atm")) {
                            terms[t] = "101325";
                        }
                    }
                    total+=data;
                    if(braces==0) {
                        for (int i = 0; i < t + 1; i++) {
                            copy[i] = terms[i];
                        }
                        calculation(t + 1);
                    }
                }
                text.setText(total);
                list.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        }else if (id == R.id.about) {
            Intent intent = new Intent(getApplicationContext(),About.class);
            startActivity(intent);
        }else if (id == R.id.help) {
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
        navigationView.setCheckedItem(R.id.simplecalculate);
        if (id == R.id.simplecalculate) {

        }
        else if (id == R.id.unitcalculate) {
            Intent intent = new Intent(getApplicationContext(),UnitConvertor.class);
            startActivity(intent);
        }
        else if (id == R.id.agecalculate) {
            Intent intent = new Intent(getApplicationContext(),Age.class);
            startActivity(intent);
        }
        else if (id == R.id.percentagecalculate) {
            Intent intent = new Intent(getApplicationContext(),Percentage.class);
            startActivity(intent);
        }
        else if (id == R.id.healthcalculate) {
            Intent intent=new Intent(getApplicationContext(),HealthCalculator.class);
            startActivity(intent);
        }
        else if (id == R.id.emicalculate) {
            Intent intent = new Intent(getApplicationContext(),EMI.class);
            startActivity(intent);
        }
        else if (id == R.id.gstcalculate) {
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
