package iss.workshop.livestreamapp.services;

import com.google.gson.Gson;

import lombok.Data;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Data
public class RetroFitService {
    private Retrofit retrofit;

    public RetroFitService (){
        initializeRetrofit();
    }

    private void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.20.6.60:8080") //IP address of the server where the spring boot application is running
        //look for IPv4 in ipconfig cmd : current = http://10.20.6.60:[port]
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

}
