package iss.workshop.livestreamapp.services;

import java.util.List;

import iss.workshop.livestreamapp.models.Stream;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginApi {

    @POST("/api/login")
    Call<List<Stream>> getAllStreams();
}
