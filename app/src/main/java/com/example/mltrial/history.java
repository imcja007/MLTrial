package com.example.mltrial;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter ;
import androidx.appcompat.app.AppCompatActivity;

public class history extends AppCompatActivity {

    private DBManager dbManager;
    Context context = history.this;
    private ListView listView;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper._ID,
            DatabaseHelper.NUMBER, DatabaseHelper.DATE };

    final int[] to = new int[] { R.id.id, R.id.number, R.id.date };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

        setContentView(R.layout.activity_history);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListiner For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView titleTextView = (TextView) view.findViewById(R.id.number);
                TextView descTextView = (TextView) view.findViewById(R.id.date);

                String id = idTextView.getText().toString();
                String title = titleTextView.getText().toString();
                String desc = descTextView.getText().toString();

                MainActivity.number=title ;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }



}
