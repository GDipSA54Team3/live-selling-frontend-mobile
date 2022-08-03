package iss.workshop.livestreamapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.agora.rtc2.Constants;
import iss.workshop.livestreamapp.adapters.ChStreamAdapter;
import iss.workshop.livestreamapp.interfaces.IStreamDetails;
import iss.workshop.livestreamapp.models.Channel;
import iss.workshop.livestreamapp.models.Stream;

public class EntranceActivity extends AppCompatActivity implements IStreamDetails {

    public ListView listOfStreams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);

        Button startA = findViewById(R.id.startA);
        Button startB = findViewById(R.id.startB);

        //populating streams
        listOfStreams = findViewById(R.id.stream_list_first);
        Channel channel = generateChannel();
        List<Stream> streamList = generateStreams(channel);

        ChStreamAdapter streamAdapter = new ChStreamAdapter(this, streamList);
        listOfStreams.setAdapter(streamAdapter);
        listOfStreams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView channelNameTxt = view
                        .findViewById(R.id.entire_row)
                        .findViewById(R.id.top_container)
                        .findViewById(R.id.text_fields)
                        .findViewById(R.id.channel_name);
                String channelName = channelNameTxt.getText().toString();

                Stream currStream = (Stream) streamAdapter.getItem(i);
                Toast.makeText(EntranceActivity.this, currStream.getName(), Toast.LENGTH_SHORT).show();

                openStreamPage("buyer", channelName, currStream.getId());
            }
        });

        //start stream A as host
        startA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStreamPage("seller", "Channel A", 1);
            }
        });

        //start stream B as host
        startB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStreamPage("seller", "Channel A", 2);
            }
        });

    }


    public void openStreamPage(String role, String channelName, long streamId){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("channelName", channelName);
        intent.putExtra("appID", getAppID());
        intent.putExtra("streamID", streamId);
        if (role.equals("seller")){
            intent.putExtra("clientRole", Constants.CLIENT_ROLE_BROADCASTER);
        } else {
            intent.putExtra("clientRole", Constants.CLIENT_ROLE_AUDIENCE);
        }


        startActivity(intent);
    }
}