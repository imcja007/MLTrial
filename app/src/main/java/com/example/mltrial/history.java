package com.example.mltrial;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class history extends AppCompatActivity {

    private DBManager dbManager;
    Context context = history.this;
    private ListView listView;
    final String[] from = new String[]{
            DatabaseHelper.NUMBER, DatabaseHelper.DATE};
    private SimpleCursorAdapter adapter;
    final int[] to = new int[]{R.id.number, R.id.date};
    Button deleteRecord;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deleteRecord = findViewById(R.id.deleteRecord);
        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();


        final TextView descTextView = (TextView) findViewById(R.id.empty);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);
        deleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("buton clicked");

                dbManager.deleteAll();
                listView.setAdapter(null);
                descTextView.setText("History Cleared");
                adapter.notifyDataSetChanged();
            }
        });
        // OnCLickListiner For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {

                TextView titleTextView = (TextView) view.findViewById(R.id.number);


                String title = titleTextView.getText().toString();
                MainActivity.number = title;
                MainActivity.nDialog = new ProgressDialog(history.this);
                MainActivity.nDialog.setMessage("Sabr rakhlo bhai thoda..");
                MainActivity.nDialog.setTitle("Fetching Data");
                MainActivity.nDialog.setIndeterminate(false);
                MainActivity.nDialog.setCancelable(false);
                new parsing(context).execute();
//
//                Intent modify_intent = new Intent(getApplicationContext(), ModifyCountryActivity.class);
//                modify_intent.putExtra("title", title);
//                modify_intent.putExtra("desc", desc);
//                modify_intent.putExtra("id", id);
//
//                startActivity(modify_intent);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


}
