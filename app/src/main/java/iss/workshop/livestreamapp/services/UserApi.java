package iss.workshop.livestreamapp.services;

import java.util.List;

import iss.workshop.livestreamapp.models.Stream;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UserApi {

    @GET("/api/user/str")
    Call<List<Stream>> getAllStreams();

}
