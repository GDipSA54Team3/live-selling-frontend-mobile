package iss.workshop.livestreamapp.models;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Channel {
    private long id;
    private String name;
    private Bitmap profilePic;
    private String token;
    private List<Product> products;

}
