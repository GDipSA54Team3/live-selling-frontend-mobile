package iss.workshop.livestreamapp.models;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Channel {
    public String name;
    public String token;
    public List<Product> products;

}
