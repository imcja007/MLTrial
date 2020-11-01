package com.example.mltrial;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class parsing extends AsyncTask {
    static JSONObject kl ;

    protected JSONObject doInBackground(Object[] objects) {

//        if (MainActivity.pattern.matcher(MainActivity.resultText).matches()) {
        try {

          String url="http://in.carregistrationapi.com/api/reg.asmx/CheckIndia?RegistrationNumber="+MainActivity.number+"&username=Cristiano_7";

            //String url = "https://www.w3schools.com/xml/plant_catalog.xml";
            //HttpURLConnection htp= url;
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            InputStream is = connection.getInputStream();
            XmlToJson xmlToJson = new XmlToJson.Builder(is, null).build();
            kl = xmlToJson.toJson() ;
//           System.out.println(kl.toString().replace("\\/","")) ;
            System.out.println("================ doInBackground COMPLETED");
           // System.out.println(kl.toString()) ;
        } catch (IOException e) {
            e.printStackTrace();
        }

//        } else {
//          System.out.println("INVALID NUMBER PLEASE TYPE");
//
//        }
        return kl;
    }

//    @Override
//    protected void onPostExecute(Object o) {
//        super.onPostExecute(o);
////        MainActivity.textView.setText("Result     " + MainActivity.abc.replaceAll("[^a-zA-Z0-9]", "") + "\nRegex:  " + MainActivity.pattern.matcher(MainActivity.abc).matches());
//        try {
////            JSONObject temp = new JSONObject(kl.getJSONObject("Vehicle").getString("vehicleJson").toString());
//
////           MainActivity.textView.setText("Description:  " + temp.getString("Description"));
////            MainActivity.textView.append("\nName" + temp.get("Zone"));
////            MainActivity.textView.append("\nRegistration Year:  " + temp.getString("RegistrationDate"));
////            MainActivity.textView.append("\nOwner:   " + temp.getString("Owner") + "\n");
////            MainActivity.textView.append("\nLocation:  " + temp.getString("Location"));
//
////            MainActivity.textView.setText("Description:  " + temp.getString("Description")+
////            ("\nName" + temp.get("Zone"))+
////            ("\nRegistration Year:  " + temp.getString("RegistrationDate"))+
////            ("\nOwner:   " + temp.getString("Owner") + "\n")+
////            ("\nLocation:  " + temp.getString("Location")));
//
//
//            new MainActivity().printInfo(kl);
//            System.out.println("================ onPost SUCCESS");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("================ onPost ERROR");
//        }
////        MainActivity.textView.setText(txt);
////        MainActivity.textView.setText(MainActivity.number);
//        System.out.println("================ onPost COMPLETED");
//    }

}

