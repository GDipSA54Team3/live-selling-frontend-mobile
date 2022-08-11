package iss.workshop.livestreamapp.services;

import java.util.List;

import iss.workshop.livestreamapp.models.Orders;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OrdersApi {

    @GET("/api/orders/purchases/{userId}")
    Call<List<Orders>> getUserPurchases(@Path("userId") String userId);

    @GET("/api/orders/channelorders/{channelId}")
    Call<List<Orders>> getChannelOrders(@Path("channelId") String channelId);
}
