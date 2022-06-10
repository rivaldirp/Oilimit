package com.example.oilimit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    private static final String TAG = AdminActivity.class.getSimpleName();

    SessionManager sessionManager;
    Button btnAdminUpdate;
    TextView etAdminLiter;
    TextView tvAdminData;
    String iden, lilit, getPw;
    private static String URL_EDIT = "http://192.168.0.103/oilimit/edit_detail.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        sessionManager = new SessionManager(AdminActivity.this);

        tvAdminData = findViewById(R.id.tvAdminData);
        etAdminLiter = findViewById(R.id. etAdminLiter);

        iden = sessionManager.getUserScanDetail().get(SessionManager.NAMAA);
        lilit = sessionManager.getUserScanDetail().get(SessionManager.LITERR);

        tvAdminData.setText(iden);
        etAdminLiter.setText(lilit);


        btnAdminUpdate = (Button) findViewById(R.id.btnAdminUpdate);

        HashMap<String, String> user = sessionManager.getUserScanDetail();
        getPw = user.get(SessionManager.NAMAA);


        //Tombol
        btnAdminUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String angka = etAdminLiter.getText().toString();

                if(Integer.parseInt(angka) <= Integer.parseInt(lilit)){
                    SaveEditDetail();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "Permintaan ditolak!", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void SaveEditDetail(){
        final String etAdminLiter = this.etAdminLiter.getText().toString().trim();
        final String pw = getPw;

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG, response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject("response");
                            String success = jsonObject.getString("SUCCESS");

                            if (success.equals("1")){
                                Toast.makeText(AdminActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                sessionManager.createSession(etAdminLiter, pw);
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(AdminActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(AdminActivity.this, "Error "+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("literr", etAdminLiter);
                params.put("namaa", pw);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}