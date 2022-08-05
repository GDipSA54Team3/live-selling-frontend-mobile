package iss.workshop.livestreamapp.models;

import androidx.navigation.NavType;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

@Data
public class User implements Serializable {

    private String username;
    private String password;
    private int id;
    //private Channel channel;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.id = 0;
    }

}
