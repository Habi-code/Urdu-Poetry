package com.Habib.habibapp.Response;

import com.Habib.habibapp.Models.PoetryModels;

import java.util.List;

public class GetPoetryResponse {
    String status;
    String message;
    List<PoetryModels> data;

    public GetPoetryResponse(String status, String message, List<PoetryModels> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PoetryModels> getData() {
        return data;
    }

    public void setData(List<PoetryModels> data) {
        this.data = data;
    }
}
