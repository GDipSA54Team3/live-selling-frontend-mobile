package iss.workshop.livestreamapp.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class StreamLog {

    private String id;
    private int numLikes;
    private Stream stream;
    private List<Message> messages;

    public StreamLog(int numLikes) {
        this.numLikes = numLikes;
        this.messages = new ArrayList<Message>();
    }
}
