package com.example.mltrial;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class infoPage extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);
        TextView model = findViewById(R.id.model);
        TextView regDate = findViewById(R.id.regDate);
        TextView owner = findViewById(R.id.owner);
        TextView location = findViewById(R.id.location);
        ImageView i = (ImageView)findViewById(R.id.image);

//The second parameter below is the default string returned if the value is not there.
        try {
            JSONObject jk = parsing.kl;
            //JSONObject temp = new JSONObject(jk.getJSONObject("Vehicle").getString("vehicleJson"));

          //  textView.setMovementMethod(new ScrollingMovementMethod());
//
//            System.out.println("=====================  infoPage");
//
//            System.out.println("Description:  " + temp.getString("Description") +
//                    "\nRegistration Year:  " + temp.getString("RegistrationDate") +
//                    "\nOwner:   " + temp.getString("Owner") + "\n" +
//                    "\nLocation:  " + temp.getString("Location"));
//
//            textView.setText("Description:  " + temp.getString("Description") +
//                    "\nRegistration Year:  " + temp.getString("RegistrationDate") +
//                    "\nOwner:   " + temp.getString("Owner") + "\n" +
//                    "\nLocation:  " + temp.getString("Location"));

//
//            model.setText("Description:  " + temp.getString("Description") );
//            regDate.setText("Registration Year:  " + temp.getString("RegistrationDate"));
//            owner.setText("Owner:   " + temp.getString("Owner"));
//            location.setText("Location:  " + temp.getString("Location"));
           // Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent());
        //    i.setImageBitmap(bitmap);


        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Catch block===========\n" + parsing.kl.toString());
            model.setText("Description:  " + "?????????????????");
            regDate.setText("Registration Year:  " + "?????????????????");
            owner.setText("Owner:   " + "?????????????????");
            location.setText("Location:  " + "?????????????????");
        }

    }
}