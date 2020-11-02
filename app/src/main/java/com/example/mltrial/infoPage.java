package com.example.mltrial;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class infoPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);
        TextView textView = findViewById(R.id.textView);

//The second parameter below is the default string returned if the value is not there.
        try {
            JSONObject jk = parsing.kl;
            JSONObject temp = new JSONObject(jk.getJSONObject("Vehicle").getString("vehicleJson"));

            textView.setMovementMethod(new ScrollingMovementMethod());

            System.out.println("=====================  infoPage");

            System.out.println("Description:  " + temp.getString("Description") +
                    "\nRegistration Year:  " + temp.getString("RegistrationDate") +
                    "\nOwner:   " + temp.getString("Owner") + "\n" +
                    "\nLocation:  " + temp.getString("Location"));

            textView.setText("Description:  " + temp.getString("Description") +
                    "\nRegistration Year:  " + temp.getString("RegistrationDate") +
                    "\nOwner:   " + temp.getString("Owner") + "\n" +
                    "\nLocation:  " + temp.getString("Location"));


        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
            System.out.println("Catch block===========\n" + parsing.kl.toString());
            textView.setText("ERRRROOOOORR");
        }

    }
}