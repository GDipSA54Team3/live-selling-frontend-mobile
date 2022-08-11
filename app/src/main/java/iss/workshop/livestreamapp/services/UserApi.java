package iss.workshop.livestreamapp.services;

import iss.workshop.livestreamapp.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    @POST("/api/user/register/{channelName}/{username}/{password}/{address}")
    Call<User> addNewUser(@Body User user, @Path("username") String username, @Path("password") String password, @Path("address") String address, @Path("channelName") String channelName);

}
