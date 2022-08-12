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

import iss.workshop.livestreamapp.helpers.ChannelDeserializer;
import iss.workshop.livestreamapp.helpers.StreamDeserializer;
import iss.workshop.livestreamapp.helpers.UserDeserializer;
import iss.workshop.livestreamapp.models.ChannelStream;
import iss.workshop.livestreamapp.models.Stream;
import iss.workshop.livestreamapp.models.User;
import lombok.Data;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Data
public class RetroFitService {
    private final String API_URL = "http://10.0.2.2:8080";
    private Retrofit retrofit;

    public RetroFitService(String type){
        initializeRetrofit(type);
    }

    private static Converter.Factory createGsonConverter(Type type, Object typeAdapter) {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(type, typeAdapter)
                .setDateFormat("yyyy-MM-dd'T'HH:mm");
        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }

    private void initializeRetrofit(String type) {

        switch(type){
            case("stream"):
                retrofit = new Retrofit.Builder()
                        .baseUrl(API_URL)
                        .addConverterFactory(createGsonConverter(Stream.class, new StreamDeserializer()))
                        .build();
                break;
            case("login"):
                retrofit = new Retrofit.Builder()
                        .baseUrl(API_URL)
                        .addConverterFactory(createGsonConverter(User.class, new UserDeserializer()))
                        .build();
                break;
            case("channel"):
                retrofit = new Retrofit.Builder()
                        .baseUrl(API_URL)
                        .addConverterFactory(createGsonConverter(ChannelStream.class, new ChannelDeserializer()))
                        .build();
                break;
            case("get-channel-from-id"):
            case("save-user"):
            case("save-channel"):
            case("save-stream"):
                retrofit = new Retrofit.Builder()
                        .baseUrl(API_URL)
                        .addConverterFactory(GsonConverterFactory.create(new Gson()))
                        .build();
                break;
            case("dashboard"):
                retrofit = new Retrofit.Builder()
                        .baseUrl(API_URL)
                        .addConverterFactory(createGsonConverter(User.class, new UserDeserializer()))
                        .build();
                break;

        }

    }

}
