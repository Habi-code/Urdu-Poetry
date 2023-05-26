package com.Habib.habibapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.Habib.habibapp.APIs.ApiClients;
import com.Habib.habibapp.APIs.ApiInstance;
import com.Habib.habibapp.Adapters.PoetryAdapters;
import com.Habib.habibapp.Models.PoetryModels;
import com.Habib.habibapp.Response.GetPoetryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    ApiInstance apiInstance;
    RecyclerView recyclerView;
    PoetryAdapters poetryAdapters;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializaion();
        setSupportActionBar(toolbar);
        getData();
    }

    private void initializaion() {
        recyclerView = findViewById(R.id.poetry_RecyclerView);
        Retrofit retrofit = ApiClients.getClients();
        apiInstance = retrofit.create(ApiInstance.class);
        toolbar = findViewById(R.id.main_toolbar_addPoetry);
    }

    private void setAdapters(List<PoetryModels> poetryModels) {
        poetryAdapters = new PoetryAdapters(poetryModels, MainActivity.this);
        recyclerView.setAdapter(poetryAdapters);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void getData() {
        apiInstance.getPoetry().enqueue(new Callback<GetPoetryResponse>() {
            @Override
            public void onResponse(Call<GetPoetryResponse> call, Response<GetPoetryResponse> response) {

                try {
                    if (response.body().getStatus().equals("1")) {
                        setAdapters(response.body().getData());
                    } else {
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.e("exp", e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<GetPoetryResponse> call, Throwable t) {
                Log.e("Failure", t.getLocalizedMessage());

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add_poetry) {
            Intent intent = new Intent(this, AddPoetry.class);
            startActivity(intent);
            Toast.makeText(this, "Add Poetry Clicked", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.settings) {
            Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}