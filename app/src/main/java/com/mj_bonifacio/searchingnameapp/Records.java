package com.mj_bonifacio.searchingnameapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import java.util.ArrayList;

public class Records extends ListActivity {
    SQLiteDatabase Conn;
    ArrayList<String> ItemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Conn = new SQLiteDatabase(this);
        ItemList = Conn.GetAllRecords();
        if(ItemList.size() > 0){
            setListAdapter(new ArrayAdapter<String>(Records.this, android.R.layout.simple_list_item_1, ItemList));
        }
        else{
            Toast.makeText(this, "No Record/s Found!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String selectedItem = ItemList.get(position);
        String[] parts = selectedItem.split(" ");
        if (parts.length >= 1) {
            int recordId = Integer.parseInt(parts[0]);
            Intent intent = new Intent(Records.this, RecordsLayout.class);
            intent.putExtra("selectedData", recordId);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Records.this, MainActivity.class);
        startActivity(intent);
    }
}
