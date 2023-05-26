package com.Habib.habibapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.Habib.habibapp.APIs.ApiClients;
import com.Habib.habibapp.APIs.ApiInstance;
import com.Habib.habibapp.Response.deleteResponse;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPoetry extends AppCompatActivity {
    Toolbar toolbar;
    TextInputEditText ETpoetryData, ETpoetName;
    AppCompatButton btnAddPoetry;
    ApiInstance apiInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poetry);

        initialization();
        setUpToolbar();

        btnAddPoetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String poetryDataString = ETpoetryData.getText().toString();
                String poetNameString = ETpoetName.getText().toString();

                if (poetryDataString.equals("")) {
                    ETpoetryData.setError("Poetry Data is Empty..!");
                } else {
                    if (poetNameString.equals("")) {
                        ETpoetName.setError("Poet Name is Empty..!");
                    } else {
                        callApi(poetryDataString,poetNameString);
                        Toast.makeText(AddPoetry.this, "Call Api", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }

    private void initialization() {
        toolbar = findViewById(R.id.main_toolbar_addPoetry);
        ETpoetName = findViewById(R.id.EVPoetName);
        ETpoetryData = findViewById(R.id.EVPoetryData);
        btnAddPoetry = findViewById(R.id.btnAddPoetry);

        Retrofit retrofit = ApiClients.getClients();
        apiInstance = retrofit.create(ApiInstance.class);
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callApi(String poetryData, String poetName) {
        apiInstance.addPoetry(poetryData, poetName).enqueue(new Callback<deleteResponse>() {
            @Override
            public void onResponse(Call<deleteResponse> call, Response<deleteResponse> response) {
                try {
                    if (response.body().getStatus().equals("1")){
                        Toast.makeText(AddPoetry.this, "Poetry Added Successfully.", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(AddPoetry.this, "Poetry Not Added Successfully.", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    Log.e("Failure", e.getLocalizedMessage());

                }
            }

            @Override
            public void onFailure(Call<deleteResponse> call, Throwable t) {
                Log.e("Failure", t.getLocalizedMessage());
            }
        });
    }

}