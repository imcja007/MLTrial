package com.example.mltrial;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class infoPage extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.nDialog.dismiss();
        setContentView(R.layout.activity_info_page);
//        TextView model = findViewById(R.id.model);
//        TextView regDate = findViewById(R.id.regDate);
//        TextView owner = findViewById(R.id.owner);
//        TextView location = findViewById(R.id.location);
//        TextView engine = findViewById(R.id.engine);
//        TextView insurance = findViewById(R.id.insurance);
//        TextView chassis = findViewById(R.id.chassis);
//        TextView fuel = findViewById(R.id.fuel);
        TextView info = findViewById(R.id.info);


//The second parameter below is the default string returned if the value is not there.
        try {
            JSONObject temp = parsing.kl;

//            System.out.println("=====================  infoPage");


//
            info.setText("Model:  " + temp.getString("Model") +
                    "\n\nRegistration Date:  " + temp.getString("Reg Date") +
                    "\n\nOwner:   " + temp.getString("Owner Name") +
                    "\n\nRegisteration Number:   " + temp.getString("Registeration Number") +
                    "\n\nRegistering Authority:  " + temp.getString("Registering Authority") +
                    "\n\nChassis Number:  " + temp.getString("Chassis Number") +
                    "\n\nEngine Number:  " + temp.getString("Engine Number") +
                    "\n\nInsurance expiry:  " + temp.getString("Insurance expiry"));

            System.out.println("Model:  " + temp.getString("Model") +
                    "\n\nRegistration Date:  " + temp.getString("Reg Date") +
                    "\n\nOwner:   " + temp.getString("Owner Name") +
                    "\n\nRegisteration Number:   " + temp.getString("Registeration Number") +
                    "\n\nRegistering Authority:  " + temp.getString("Registering Authority") +
                    "\n\nChassis Number:  " + temp.getString("Chassis Number") +
                    "\n\nEngine Number:  " + temp.getString("Engine Number") +
                    "\n\nInsurance expiry:  " + temp.getString("Insurance expiry"));

//            fuel.setText("Fuel Type  " + temp.getString("Fuel Type"));            model.setText("Model:  " + temp.getString("Model") );
//            regDate.setText("Registration Date:  " + temp.getString("Reg Date"));
//            owner.setText("Owner:   " + temp.getString("Owner Name"));
//            location.setText("Registering Authority:  " + temp.getString("Registering Authority"));
//            chassis.setText("Chassis Number  " + temp.getString("Chassis Number"));
//            engine.setText("Engine Number  " + temp.getString("Engine Number"));
//            insurance.setText("Insurance expiry  " + temp.getString("Insurance expiry"));
//            fuel.setText("Fuel Type  " + temp.getString("Fuel Type"));


        } catch (NullPointerException | JSONException e) {

            System.out.println("Catch block===========ERRROOOOOOR\n" + e.toString());
            info.setText("Ehhh Something's wrong");
        }

    }
}