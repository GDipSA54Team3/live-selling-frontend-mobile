package iss.workshop.livestreamapp.models;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChannelStream implements Serializable{

    private Bitmap profilePic;
    private String token;
    private List<Product> products;
    private String id;
    private String name;
    private User user;


    //private List<Rating> ratings;

    //private List<OrderProduct> orders;

    private List<Stream> streams;

    public ChannelStream(String name, User user) {
        this.name = name;
        //this.products = new ArrayList<>();
        //this.ratings = new ArrayList<>();
        //this.orders = new ArrayList<>();
        //this.streams = new ArrayList<>();
        this.user = user;
    }
}
