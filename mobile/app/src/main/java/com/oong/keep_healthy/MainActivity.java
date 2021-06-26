package com.oong.keep_healthy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.oong.keep_healthy.config.ApiClient;
import com.oong.keep_healthy.config.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextInputEditText editUsername, editPassword;
    private Button btnLogin;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        btnLogin.setOnClickListener(v -> {
//            String username = editUsername.getText().toString();
//            Toast.makeText(this, username, Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
            prosesLogin(editUsername.getText().toString(), editPassword.getText().toString());
        });
    }

    private void initView() {
        progressBar = findViewById(R.id.progressBar);
        editUsername = findViewById(R.id.edit_username);
        editPassword = findViewById(R.id.edit_password);
        btnLogin = findViewById(R.id.btn_login);
    }

    private void prosesLogin(String username, String password) {
        apiInterface.login(username, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("SUCCESS", "Success");
                String res = response.body().toString();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    if(jsonObject.getString("status").equals("success")) {
                        String obj = jsonObject.getString("data");
                        JSONObject body = new JSONObject(obj);
                        Intent goHome = new Intent(MainActivity.this, HomeActivity.class);
                        goHome.putExtra("name", body.getString("name"));
                        goHome.putExtra("username", body.getString("username"));
                        startActivity(goHome);
                        MainActivity.this.finish();
                    }
                    else if(jsonObject.getString("status").equals("Unauthorized")) {
                        Toast.makeText(MainActivity.this, "Password salah", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Akun tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                finally {
                    progressBar.setVisibility(View.GONE);
                }
                Log.d("RES", jsonObject.toString());
                Log.d("RESPONSE", response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("RESPONSE", "FAILED " + t.getMessage());
                Log.d("FAILED", "Gagal Bro");
            }
        });
    }
}