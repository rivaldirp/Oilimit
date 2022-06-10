package com.example.oilimit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PenjualActivity extends AppCompatActivity {

    TextView tvData, tvLiter;
    SessionManager sessionManager;
    String nama, liter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjual);
        sessionManager = new SessionManager(PenjualActivity.this);

        tvData = findViewById(R.id.tvData);
        tvLiter = findViewById(R.id.tvLiter);

        nama = sessionManager.getUserScanDetail().get(SessionManager.NAMAA);
        liter = sessionManager.getUserScanDetail().get(SessionManager.LITERR);

        tvData.setText(nama);
        tvLiter.setText(liter);

        final EditText etAdmin = findViewById(R.id.etAdmin);
        Button btnAdmin = findViewById(R.id.btnAdmin);
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String admin = "113333";
                String input_admin = etAdmin.getText().toString();
                if(input_admin.equals(admin)){
                    Toast.makeText(getApplicationContext(), "Berhasil masuk", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PenjualActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "Pin Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}