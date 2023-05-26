package com.Habib.habibapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Habib.habibapp.APIs.ApiClients;
import com.Habib.habibapp.APIs.ApiInstance;
import com.Habib.habibapp.Models.PoetryModels;
import com.Habib.habibapp.R;
import com.Habib.habibapp.Response.deleteResponse;
import com.Habib.habibapp.UpdatePoetry;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PoetryAdapters extends RecyclerView.Adapter<PoetryAdapters.ViewHolder> {
    List<PoetryModels> poetryModels;
    Context context;
    ApiInstance apiInstance;

    public PoetryAdapters(List<PoetryModels> poetryModels, Context context) {
        this.poetryModels = poetryModels;
        this.context = context;
        Retrofit retrofit = ApiClients.getClients();
        apiInstance = retrofit.create(ApiInstance.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_poetry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.poetName.setText(poetryModels.get(position).getPoet_name());
        holder.poetry.setText(poetryModels.get(position).getPoetry_data());
        holder.dateTime.setText(poetryModels.get(position).getDate_time());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePoetry(poetryModels.get(position).getId() + " ", position);

            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, poetryModels.get(position).getId() + "\n" + poetryModels.get(position).getPoetry_data() + "\n" + poetryModels.get(position).getPoet_name(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, UpdatePoetry.class);
                intent.putExtra("p_id", poetryModels.get(position).getId());
                intent.putExtra("p_data", poetryModels.get(position).getPoetry_data());
                intent.putExtra("p_name", poetryModels.get(position).getPoet_name());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return poetryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView poetName, poetry, dateTime;
        Button btnDelete, btnUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poetName = itemView.findViewById(R.id.poetName);
            poetry = itemView.findViewById(R.id.poetryData);
            dateTime = itemView.findViewById(R.id.timeDate);
            btnDelete = itemView.findViewById(R.id.deleteBtn);
            btnUpdate = itemView.findViewById(R.id.updateBtn);
        }
    }

    public void deletePoetry(String id, int pose) {
        apiInstance.deletePoetry(id).enqueue(new Callback<deleteResponse>() {
            @Override
            public void onResponse(Call<deleteResponse> call, Response<deleteResponse> response) {
                try {
                    if (response != null) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        if (response.body().getStatus().equals("1")) {
                            poetryModels.remove(pose);
                            notifyDataSetChanged();

                        }
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
}
