package com.example.oilimit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class PembeliActivity extends AppCompatActivity {
    private ImageView ivQr;
    private MultiFormatWriter multi = new MultiFormatWriter();


    TextView tvJeneng, tvLim;
    SessionManager sessionManager;
    String jeneng, pewe, mimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembeli);
        sessionManager = new SessionManager(PembeliActivity.this);



        tvJeneng = findViewById(R.id.tvJeneng);
        jeneng = sessionManager.getUserDetail().get(SessionManager.NAMA);
        tvJeneng.setText(jeneng);

        tvLim = findViewById(R.id.tvLim);
        mimit = sessionManager.getUserDetail().get(SessionManager.LITER);
        tvLim.setText(mimit);

        ivQr = findViewById(R.id.ivQr);

        try {
            pewe = sessionManager.getUserDetail().get(SessionManager.PASSWORDD);
            BitMatrix bitMatrix = multi.encode(pewe, BarcodeFormat.QR_CODE, 300, 300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ivQr.setImageBitmap(bitmap);
        } catch (WriterException e){
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}