package iss.workshop.livestreamapp.models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Stream {
    private long id;
    private String name;
    private String description;
    private Channel channel;
    private LocalDateTime startDate;
    private List<Product> products;
}
