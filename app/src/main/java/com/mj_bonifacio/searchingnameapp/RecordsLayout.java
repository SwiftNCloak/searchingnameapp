package com.mj_bonifacio.searchingnameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RecordsLayout extends AppCompatActivity {

    EditText Fname, Mname, Lname;
    Button btnEdit, btnDelete;
    SQLiteDatabase Conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_layout);Conn = new SQLiteDatabase(this);
        Fname = (EditText) findViewById(R.id.txtFname);
        Mname = (EditText) findViewById(R.id.txtMname);
        Lname = (EditText) findViewById(R.id.txtLname);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        int dataIndex = getIntent().getIntExtra("selectedData", 0);

        String[] existingData = Conn.getRecordDataByIndex(dataIndex);
        if (existingData != null) {
            Fname.setText(existingData[0]);
            Mname.setText(existingData[1]);
            Lname.setText(existingData[2]);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FName, MName, LName;
                FName = Fname.getText().toString();
                MName = Mname.getText().toString();
                LName = Lname.getText().toString();
                if (FName.isEmpty() || MName.isEmpty() || LName.isEmpty()) {
                    Toast.makeText(RecordsLayout.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                }
                if (Conn.recordExists(FName, MName, LName)) {
                    Toast.makeText(RecordsLayout.this, "NO CHANGES MADE!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if (Conn.updateRecord(dataIndex, FName, MName, LName)) {
                    Toast.makeText(RecordsLayout.this, "EDIT RECORD SUCCESSFUL!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RecordsLayout.this, Records.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(RecordsLayout.this, "ERROR ON SAVING!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Conn.deleteRecordByIndex(dataIndex)) {
                    Toast.makeText(RecordsLayout.this, "Record deleted successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RecordsLayout.this, Records.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RecordsLayout.this, "Error deleting record", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}