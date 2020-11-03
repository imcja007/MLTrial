package com.example.mltrial;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class parsing extends AsyncTask {
    static JSONObject kl;

    protected JSONObject doInBackground(Object[] objects) {

//        if (MainActivity.pattern.matcher(MainActivity.resultText).matches()) {
        try {

            //String url = "http://in.carregistrationapi.com/api/reg.asmx/CheckIndia?RegistrationNumber=" + MainActivity.number + "&username=Aditya123";
            String url2=" https://shrouded-falls-48764.herokuapp.com/vehicle-info/"+MainActivity.number ;
            //String url = "https://www.w3schools.com/xml/plant_catalog.xml";
//            URLConnection connection = new URL(url).openConnection();
//            connection.setRequestProperty("Accept-Charset", "UTF-8");
//            connection.setRequestProperty("API-Key","29e683451a914df882eaa2f7268a21a4");
//            InputStream is = connection.getInputStream();
            URL url = new URL(url2);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("API-Key","29e683451a914df882eaa2f7268a21a4");
            int statusCode = urlConnection.getResponseCode();
            JSONObject kl=new JSONObject() ;
            System.out.println("Status code is   " +statusCode);
            if (statusCode ==  200) {
                InputStream it = new BufferedInputStream(urlConnection.getInputStream());
                InputStreamReader read = new InputStreamReader(it);
                BufferedReader buff = new BufferedReader(read);
                StringBuilder dta = new StringBuilder();
                String chunks ;
                while((chunks = buff.readLine()) != null)
                {
                    dta.append(chunks);
                }
              System.out.println("Sting Builder is    "+dta);
                kl=new JSONObject(dta.toString()) ;
            }
            else
            {
                kl=null ;
            }
//            String isp = "<Vehicle xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://regcheck.org.uk\">\n" +
//                    "<vehicleJson>{ \"Description\": \"HONDAMCYSC/ACTIVA 3G EAS KS CBS BS3\", \"RegistrationYear\": \"2016\", \"CarMake\": { \"CurrentTextValue\": \"HONDAMCYSC\" }, \"CarModel\": { \"CurrentTextValue\": \"ACTIVA 3G EAS KS CBS BS3\" }, \"EngineSize\": { \"CurrentTextValue\": \"\" }, \"MakeDescription\": { \"CurrentTextValue\": \"HONDAMCYSC\" }, \"ModelDescription\": { \"CurrentTextValue\": \"ACTIVA 3G EAS KS CBS BS3\" }, \"VechileIdentificationNumber\": \"\", \"EngineNumber\": \"\", \"FuelType\": { \"CurrentTextValue\": \"\" }, \"RegistrationDate\": \"27/10/2016\", \"Owner\": \"*U*E*D*A *A*N\", \"Fitness\": \"\", \"Insurance\": \"NA\", \"Location\": \"BHOPAL RTO, MADHYA PRADESH\", \"ImageUrl\": \"http://in.carregistrationapi.com/image.aspx/@SE9OREFNQ1lTQy9BQ1RJVkEgM0cgRUFTIEtTIENCUyBCUzM=\" }</vehicleJson>\n" +
//                    "<vehicleData>\n" +
//                    "<Description>HONDAMCYSC/ACTIVA 3G EAS KS CBS BS3</Description>\n" +
//                    "<RegistrationYear>2016</RegistrationYear>\n" +
//                    "<CarMake>\n" +
//                    "<CurrentTextValue>HONDAMCYSC</CurrentTextValue>\n" +
//                    "</CarMake>\n" +
//                    "<CarModel>ACTIVA 3G EAS KS CBS BS3</CarModel>\n" +
//                    "</vehicleData>\n" +
//                    "</Vehicle>";
//
//            InputStream is =new ByteArrayInputStream(isp.getBytes("UTF-8"));
//
//            XmlToJson xmlToJson = new XmlToJson.Builder(is, "UTF-8").build();
//            kl = xmlToJson.toJson();
           // JSONObject kl=new JSONObject(is.toString()) ;

//           System.out.println(kl.toString().replace("\\/","")) ;
            System.out.println("================ doInBackground COMPLETED");
//            System.out.println("++++++++++++++++++++" + kl.toString());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

//        } else {
//          System.out.println("INVALID NUMBER PLEASE TYPE");
//
//        }
        System.out.println(kl);
        return kl;
    }


}

