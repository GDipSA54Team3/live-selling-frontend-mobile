package iss.workshop.livestreamapp.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Orders implements Serializable {
    //this is Tina's comment
    private String id;
    private User user;
    private LocalDateTime orderDateTime;

    private List<OrderProduct> orderProduct;

    public Orders(User user, LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
        this.orderProduct = new ArrayList<>();
        this.user = user;
    }
}
