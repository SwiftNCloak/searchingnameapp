package com.mj_bonifacio.searchingnameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText Fname, Mname, Lname;
    SQLiteDatabase Conn;
    Button BtnSave, BtnView, BtnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Conn = new SQLiteDatabase(this);
        Fname = (EditText) findViewById(R.id.txtFname);
        Mname = (EditText) findViewById(R.id.txtMname);
        Lname = (EditText) findViewById(R.id.txtLname);
        BtnSave = (Button) findViewById(R.id.btnSave);
        BtnView = (Button) findViewById(R.id.btnView);
        BtnDelete = (Button) findViewById(R.id.btnDelete);

        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FName, MName, LName;
                FName = Fname.getText().toString();
                MName = Mname.getText().toString();
                LName = Lname.getText().toString();
                if (FName.isEmpty() || MName.isEmpty() || LName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "KINDLY FILL UP THE MISSING FIELD!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Conn.recordExists(FName, MName, LName)){
                    Toast.makeText(MainActivity.this, "RECORD ALREADY EXISTS!",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(Conn.AddRecord(FName, MName, LName)){
                        Toast.makeText(MainActivity.this, "SAVING RECORD SUCCESSFUL!", Toast.LENGTH_SHORT).show();
                        Fname.setText("");
                        Mname.setText("");
                        Lname.setText("");
                    }
                    else{
                        Toast.makeText(MainActivity.this, "ERROR ON SAVING!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        BtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Records.class);
                startActivity(intent);
            }
        });

        BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Conn.DeleteRecords()){
                    Toast.makeText(MainActivity.this, "CLEAR RECORDS SUCCESSFUL!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"ERROR ON CLEARING!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}