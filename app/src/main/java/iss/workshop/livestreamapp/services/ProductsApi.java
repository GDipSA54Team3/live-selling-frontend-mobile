package iss.workshop.livestreamapp.services;

import java.util.List;

import iss.workshop.livestreamapp.models.Product;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductsApi {

    @POST("/api/product/addtostore/")
    Call<Product> addToStore(@Body Product product);

    @GET("/api/product/products/getchannelproducts/{channelId}")
    Call<List<Product>> getAllProductsInStore(@Path("channelId") String channelId);
}
