package iss.workshop.livestreamapp.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import lombok.Data;

@Data
public class Stream implements Serializable {
    private long id;
    private String name;
    private String description;
    private Channel channel;
    private LocalDateTime startDate;
    private List<Product> products;

    public Stream(){
        products = new ArrayList<Product>();
    }
}
