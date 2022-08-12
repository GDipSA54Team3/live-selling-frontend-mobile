package iss.workshop.livestreamapp.services;

import java.util.List;

import iss.workshop.livestreamapp.models.LoginBag;
import iss.workshop.livestreamapp.models.Stream;
import iss.workshop.livestreamapp.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DashboardApi {

    @GET("/api/rating/userrating/{userId}")
    Call<String> getUserAverageRating(@Path("userId") String userId);

    @GET("/api/likes/userlikes/{userId}")
    Call<String> getUserAverageLikes(@Path("userId") String userId);

    @GET("/api/likes/avgstreamlikes")
    Call<String> getAverageStreamLikes();

    @GET("/api/orders/pendingorders/{userId}")
    Call<String> getPengingOrders(@Path("userId") String userId);
}
