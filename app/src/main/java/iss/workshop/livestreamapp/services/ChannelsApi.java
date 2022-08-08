package iss.workshop.livestreamapp.services;

import java.util.List;

import iss.workshop.livestreamapp.models.ChannelStream;
import iss.workshop.livestreamapp.models.Stream;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChannelsApi {

    @GET("/api/user/channels/{channelId}")
    Call<ChannelStream> selectChannel(@Path("channelId") String channelId);

    @GET("/api/user/channels")
    Call<List<ChannelStream>> getAllChannels();
}
