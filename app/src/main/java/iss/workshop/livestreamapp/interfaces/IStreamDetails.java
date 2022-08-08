package iss.workshop.livestreamapp.interfaces;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import iss.workshop.livestreamapp.models.ChannelStream;
import iss.workshop.livestreamapp.models.Product;
import iss.workshop.livestreamapp.models.Stream;

public interface IStreamDetails {

    default String getAppID(){
        return "813f22ea50924b43ae8488edb975d02c";
    }

    default ChannelStream generateChannel(){
        ChannelStream channelStream = new ChannelStream();
        channelStream.setName("ChannelStream A");

        //change this item for token generation
        channelStream.setToken("006813f22ea50924b43ae8488edb975d02cIAAFA+4Cs0XMCcPj1uZSCm7YdswHBh/eGisZnI0U4hxmzuQQT+IAAAAAEABUJOp92bTsYgEAAQDVtOxi");
        //add other setters for testing
        return channelStream;
    };

    /*
    default List<Stream> generateStreams(ChannelStream channelStream){
        List<Stream> streams = new ArrayList<Stream>();
        Stream stream1 = new Stream();

        stream1.setId(UUID.randomUUID().toString());
        stream1.setChannelStream(channelStream);
        stream1.setTitle("First Stream");
        //stream1.setDescription("This is the first stream for " + channelStream.getName());
        stream1.setSchedule(LocalDateTime.now());

        //generate product objects
        Product product1 = new Product();
        product1.setName("Apple");
        Product product2 = new Product();
        product2.setName("Banana");
        Product product3 = new Product();
        product3.setName("Orange");
        Product product4 = new Product();
        product4.setName("Watermelon");
        Product product5 = new Product();
        product5.setName("Basket");
        Product product6 = new Product();
        product6.setName("Shoe");

        stream1.getProducts().add(product1);
        stream1.getProducts().add(product2);
        stream1.getProducts().add(product3);
        stream1.getProducts().add(product4);
        stream1.getProducts().add(product5);
        stream1.getProducts().add(product6);

        Stream stream2 = new Stream();

        stream2.setId(2);
        stream2.setChannelStream(channelStream);
        stream2.setName("Second Stream");
        stream2.setDescription("This is the second stream for " + channelStream.getName());
        stream2.setStartDate(LocalDateTime.now());

        Stream stream3 = new Stream();

        stream3.setId(3);
        stream3.setChannelStream(channelStream);
        stream3.setName("Third Stream");
        stream3.setDescription("This is the third stream for " + channelStream.getName());
        stream3.setStartDate(LocalDateTime.now());

        streams.add(stream1);
        streams.add(stream2);
        streams.add(stream3);
        return streams;
    }

     */

    default String generateUserId(){
        return UUID.randomUUID().toString();
    }
}
