package iss.workshop.livestreamapp.models;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product implements Serializable {

    private long Id;
    private String name;
    private double price;

}
