package iss.workshop.livestreamapp.services;

import iss.workshop.livestreamapp.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    @POST("/api/user/register/{channelName}")
    Call<User> addNewUser(@Body User user, @Path("channelName") String channelName);

}
