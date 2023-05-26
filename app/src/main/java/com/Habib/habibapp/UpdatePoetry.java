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

public class UpdatePoetry extends AppCompatActivity {
    Toolbar toolbar;
    TextInputEditText poetryDataTV, poetNameTV;
    AppCompatButton btnUpdate;
    int poetryId;
    String poetryDataString;
    String poetNameString;
    ApiInstance apiInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_poetry);

        initialization();
        setUpToolBar();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatePoetryData = poetryDataTV.getText().toString();
                String updatePoetName = poetNameTV.getText().toString();

                if (updatePoetryData.equals("")) {
                    poetryDataTV.setError("Poetry Data is Empty!");
                } else {
                    if (updatePoetName.equals("")) {
                        poetNameTV.setError("Poet Name is Empty!");
                    } else {
                        CallUpdateApi(updatePoetryData, updatePoetName, poetryId + "");
                        Toast.makeText(UpdatePoetry.this, "Call Api", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });

    }

    private void setUpToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void CallUpdateApi(String poetryData, String poetName, String pId) {
        apiInstance.updatePoetry(poetryData, poetName, pId).enqueue(new Callback<deleteResponse>() {
            @Override
            public void onResponse(Call<deleteResponse> call, Response<deleteResponse> response) {
                try {
                    if (response.body().getStatus().equals("1")) {
                        Toast.makeText(UpdatePoetry.this, "Data Updated", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(UpdatePoetry.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.e("exp", e.getLocalizedMessage());

                }
            }

            @Override
            public void onFailure(Call<deleteResponse> call, Throwable t) {
                Log.e("Failure", t.getLocalizedMessage());
            }
        });

    }

    private void initialization() {
        toolbar = findViewById(R.id.main_toolbar_updatePoetry);
        poetryDataTV = findViewById(R.id.EVUpdatePoetryData);
        poetNameTV = findViewById(R.id.EVUpdatePoetName);
        btnUpdate = findViewById(R.id.btnUpdatePoetry);

        Retrofit retrofit = ApiClients.getClients();
        apiInstance = retrofit.create(ApiInstance.class);

        poetryId = getIntent().getIntExtra("p_id", 0);
        poetryDataString = getIntent().getStringExtra("p_data");
        poetNameString = getIntent().getStringExtra("p_name");

        poetryDataTV.setText(poetryDataString);
        poetNameTV.setText(poetNameString);


    }
}