package iss.workshop.livestreamapp.services;

import java.util.List;

import iss.workshop.livestreamapp.models.Stream;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface StreamsApi {

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("/api/user/streams")
    Call<List<Stream>> getAllStreams();

    @GET("/api/user/userstreams/{userId}")
    Call<List<Stream>> getAllUserStreams(@Path("userId") String userId);
}
