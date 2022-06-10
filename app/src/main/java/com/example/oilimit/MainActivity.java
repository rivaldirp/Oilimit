package com.example.oilimit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.example.oilimit.api.ApiClient;
import com.example.oilimit.api.ApiInterface;
import com.example.oilimit.model.cari.Cari;
import com.example.oilimit.model.cari.CariData;
import com.example.oilimit.model.login.LoginData;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_QR_SCAN = 101;
    private Button btnPembeli;
    private Button btnPenjual;

    private String KEY_RESULT = "RESULT";

    ApiInterface apiInterface;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(MainActivity.this);
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        btnPembeli = (Button) findViewById(R.id.btnPembeli);
        btnPembeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPembeli();
            }
        });

        btnPenjual = (Button) findViewById(R.id.btnPenjual);
        btnPenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent i = new Intent(MainActivity.this, QrCodeActivity.class);
                                startActivityForResult( i,REQUEST_CODE_QR_SCAN);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                permissionDeniedResponse.getRequestedPermission();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                            }
                        }).check();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            if (data == null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if (result != null) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("Gagal Melakukan Scan");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return;

        }
        if (requestCode == REQUEST_CODE_QR_SCAN) {
            if (data == null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            cari(result);


        }
    }

    private void cari(String result) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Cari> cariCall = apiInterface.cariResponse(result);
        cariCall.enqueue(new Callback<Cari>() {
            @Override
            public void onResponse(Call<Cari> call, Response<Cari> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){

                    sessionManager = new SessionManager(MainActivity.this);
                    CariData cariData = response.body().getData();
                    sessionManager.createCariSession(cariData);

                    Toast.makeText(MainActivity.this, response.body().getData().getNamaa(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, PenjualActivity.class);
                    startActivity(i);
                }else {
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cari> call, Throwable t) {

            }
        });
    }


    private void openPembeli() {
        Intent intent = new Intent(this, PembeliActivity.class);
        startActivity(intent);
    }

    private void moveToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionLogout:
                sessionManager.logoutSession();
                moveToLogin();
        }
        return super.onOptionsItemSelected(item);
    }



}