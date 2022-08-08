package iss.workshop.livestreamapp.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import iss.workshop.livestreamapp.helpers.StreamStatus;
import lombok.Data;

@Data
public class Stream implements Serializable {

    private String id;
    private String title;
    private LocalDateTime schedule;
    private ChannelStream channelStream;
    private StreamLog log;
    private StreamStatus status;

    public Stream(String title, LocalDateTime schedule, ChannelStream channel, StreamStatus status) {
        this.title = title;
        this.schedule = schedule;
        this.channelStream = channel;
        this.status = status;

    }

    /*
    private long id;
    private String name;
    private String description;
    private ChannelStream channelStream;
    private LocalDateTime startDate;
    private List<Product> products;

    public Stream(){
        products = new ArrayList<Product>();
    }
    */

}
