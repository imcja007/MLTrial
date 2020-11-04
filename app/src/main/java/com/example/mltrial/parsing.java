package com.example.mltrial;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

public class parsing extends AsyncTask {

    @SuppressLint("StaticFieldLeak")
    private Context context;

    public parsing(Context  context){
        this.context= context;
    }
    static JSONObject kl;

    protected JSONObject doInBackground(Object[] objects) {
      // MainActivity.pgsBar.setVisibility(View.VISIBLE);

//        if (MainActivity.pattern.matcher(MainActivity.resultText).matches()) {
        try {
            System.out.println("Number recieved is     "+MainActivity.number);
            //String url = "http://in.carregistrationapi.com/api/reg.asmx/CheckIndia?RegistrationNumber=" + MainActivity.number + "&username=Aditya123";
            String url2 = " https://shrouded-falls-48764.herokuapp.com/vehicle-info/" + MainActivity.number;
//            String url2 = " https://shrouded-falls-48764.herokuapp.com/vehicle-info/MP04SV8479";
            //String url = "https://www.w3schools.com/xml/plant_catalog.xml";

            URL url = new URL(url2);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
           urlConnection.setRequestProperty("API-Key", "f046707c4bfa4f13bdde752cd1fcbd0d");
//            urlConnection.setRequestProperty("API-Key", "29e683451a914df882eaa2f7268a21a4"); //chirag key
            int statusCode = urlConnection.getResponseCode();
            kl = new JSONObject();
            System.out.println("Status code is   " + statusCode);
            if (statusCode == 200) {
                InputStream it = new BufferedInputStream(urlConnection.getInputStream());
                InputStreamReader read = new InputStreamReader(it);
                BufferedReader buff = new BufferedReader(read);
                StringBuilder dta = new StringBuilder();
                String chunks;
                while ((chunks = buff.readLine()) != null) {
                    dta.append(chunks);
                }
                System.out.println("Sting Builder is    " + dta);
                kl = new JSONObject(dta.toString());
            } else {
                kl = null;
            }

//           System.out.println(kl.toString().replace("\\/","")) ;
            System.out.println("================ doInBackground COMPLETED");
//
//            Intent i = new Intent(MainActivity.this, infoPage.class);
//
//
//            //pgsBar.setVisibility(View.GONE);
//            startActivity(i);
        } catch (IOException | JSONException |NullPointerException e) {
            e.printStackTrace();
        }

//        } else {
//          System.out.println("INVALID NUMBER PLEASE TYPE");
//
//        }
        System.out.println(kl);
        return kl;
    }

    @Override
    protected void onPreExecute() {
        MainActivity.nDialog.show();
    }

    @Override
    protected void onPostExecute(Object o) {
        Intent i = new Intent(context.getApplicationContext(), infoPage.class);
        context.startActivity(i);

    }
}

