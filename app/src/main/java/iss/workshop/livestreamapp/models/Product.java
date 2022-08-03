package iss.workshop.livestreamapp.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {

    public long Id;
    public String name;
    public double price;
}
