package iss.workshop.livestreamapp.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {

    private long Id;
    private String name;
    private double price;
}
