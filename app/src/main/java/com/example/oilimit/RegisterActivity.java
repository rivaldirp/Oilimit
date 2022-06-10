package com.example.oilimit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oilimit.api.ApiClient;
import com.example.oilimit.api.ApiInterface;
import com.example.oilimit.model.register.Register;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etNohp, etPassword, etNama;
    Button btnRegister;
    TextView tvLogin;
    String Password, Name;
    String Nohp;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNohp = findViewById(R.id.etRegisterNohp);
        etPassword = findViewById(R.id.etRegisterPassword);
        etNama = findViewById(R.id.etRegisterName);
        btnRegister =findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                Nohp = etNohp.getText().toString();
                Password = etPassword.getText().toString();
                Name = etNama.getText().toString();
                register(Nohp, Password, Name);
                break;
            case R.id.tvLogin:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void register(String nohp, String password, String name) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Register> call = apiInterface.registerResponse(nohp, password, name);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}