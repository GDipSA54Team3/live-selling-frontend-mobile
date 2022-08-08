package iss.workshop.livestreamapp.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import iss.workshop.livestreamapp.helpers.StreamDeserializer;
import iss.workshop.livestreamapp.models.Stream;
import lombok.Data;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Data
public class RetroFitService {
    private Retrofit retrofit;

    public RetroFitService (){
        initializeRetrofit();
    }


    private static Converter.Factory createGsonConverter(Type type, Object typeAdapter) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type, typeAdapter);
        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }


    private void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.50.4.140:8080")
                .addConverterFactory(createGsonConverter(Stream.class, new StreamDeserializer()))
                .build();
    }

}
