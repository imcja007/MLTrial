package com.example.mltrial;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class infoPage extends AppCompatActivity {


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        HashMap<String, String> hash = new HashMap<String, String>();
        ListView listView1 = findViewById(R.id.listView);
        MainActivity.nDialog.dismiss();


//The second parameter below is the default string returned if the value is not there.
        try {
            JSONObject temp = parsing.kl;

//            System.out.println("=====================  infoPage");

            hash.put("Owner", temp.getString("Owner Name"));
            hash.put("Registration Date", temp.getString("Reg Date"));
            hash.put("Registering Authority", temp.getString("Registering Authority"));
            hash.put("Model", temp.getString("Model"));
            hash.put("Registration Number", temp.getString("Registeration Number"));
            hash.put("Fuel Type", temp.getString("Fuel Type"));
            hash.put("Chassis Number", temp.getString("Chassis Number"));
            hash.put("Engine Number", temp.getString("Engine Number"));
            hash.put("Insurance expiry", temp.getString("Insurance expiry"));

            ArrayList<String> x = new ArrayList<>(hash.keySet());
            ArrayList<String> y = new ArrayList<>(hash.values());
            System.out.println("$$$$$$$$$$$$$$$$$$$" + x);
            System.out.println("$$$$$$$$$$$$$$$$$$$" + y);
            MyAdapter adapter = new MyAdapter(this, x, y);
            listView1.setAdapter(adapter);


            System.out.println("Model:  " + temp.getString("Model") +
                    "\n\nRegistration Date:  " + temp.getString("Reg Date") +
                    "\n\nOwner:   " + temp.getString("Owner Name") +
                    "\n\nRegisteration Number:   " + temp.getString("Registeration Number") +
                    "\n\nRegistering Authority:  " + temp.getString("Registering Authority") +
                    "\n\nFuel Type:  " + temp.getString("Fuel Type") +
                    "\n\nChassis Number:  " + temp.getString("Chassis Number") +
                    "\n\nEngine Number:  " + temp.getString("Engine Number") +
                    "\n\nInsurance expiry:  " + temp.getString("Insurance expiry"));


        } catch (NullPointerException | JSONException e) {

            System.out.println("Catch block===========ERRROOOOOOR\n" + e.toString());
//            info.setText("Ehhh Something's wrong");
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> x;
        ArrayList<String> y;

        MyAdapter(Context c, ArrayList<String> x, ArrayList<String> y) {

            super(c, R.layout.row, R.id.textView2, x);

            this.context = c;
            this.x = x;
            this.y = y;


        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);

            TextView myTitle = row.findViewById(R.id.textView2);
            TextView myDescription = row.findViewById(R.id.textView1);

            // now set our resources on views

            myTitle.setText(x.get(position));
            myDescription.setText(y.get(position));


            return row;
        }
    }
}