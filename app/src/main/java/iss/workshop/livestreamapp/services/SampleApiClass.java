package iss.workshop.livestreamapp.services;

import java.util.List;

import iss.workshop.livestreamapp.models.Stream;
import retrofit2.Call;
import retrofit2.http.*;

public interface SampleApiClass {
    //this class will serve as an example, might need to create more helper classes for api fetches

    @GET("stream/get-all") // this will have the request URl that will make the server fetch the list of streams
    Call<List<Stream>> getAllStreams();

    @POST("stream/get-all") // this will have the request URl that will make the stream save the stream
    Call<Stream> saveStream(@Body Stream stream); //body annotation will save the entity in the post body

    @DELETE("stream/delete")
    void delete();
}

/*
=========== FOR POST ============
TextView item = findViewById
TextView item2 = findViewById

RetrofitService rfServ = new RetrofitService
SampleApiClass sac = rfServ.getRetrofit().create(SampleApiClass.class);

setOnClickListener(
    String name = String.valueOf(item.getText());
    .
    .
    .

    Entity entity = new Entity();
    -- call the setters

    sac.call_function(entity)
        .enqueue(Callback<Entity>
            onResponse: on success
            onFailure: on failure
        )

)

===========================

========== FOR GET ===========

RetrofitService rfServ = new RetrofitService
SampleApiClass sac = rfServ.getRetrofit().create(SampleApiClass.class);

sac.getAll().enqueue(CallBack<List<Entity>>(){
    onResponse() populateList(response.body())
    onFailure: toast maybe?
})

populateList(List<Entity> body){
       //create ListAdapter passing the listinside the adapter
       //.setAdapter on List (classic code)
}

===============================

 */