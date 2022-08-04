package iss.workshop.livestreamapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import io.agora.rtc2.Constants;
import iss.workshop.livestreamapp.adapters.ChStreamAdapter;
import iss.workshop.livestreamapp.interfaces.IMenuAccess;
import iss.workshop.livestreamapp.interfaces.IStreamDetails;
import iss.workshop.livestreamapp.models.Channel;
import iss.workshop.livestreamapp.models.Stream;

public class EntranceActivity extends AppCompatActivity implements IStreamDetails, IMenuAccess {

    public ListView listOfStreams;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public Channel channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);

        Button startA = findViewById(R.id.startA);
        Button startB = findViewById(R.id.startB);

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //populating streams
        listOfStreams = findViewById(R.id.stream_list_first);
        channel = generateChannel();
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

                openStreamPage("buyer", channel, currStream.getId());
            }
        });

        //start stream A as host
        startA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStreamPage("seller", channel, 1);
            }
        });

        //start stream B as host
        startB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStreamPage("seller", channel, 2);
            }
        });

    }


    public void openStreamPage(String role, Channel channel, long streamId){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("channelName", channel.getName());
        intent.putExtra("token", channel.getToken());
        intent.putExtra("appID", getAppID());
        intent.putExtra("streamID", streamId);
        if (role.equals("seller")){
            intent.putExtra("clientRole", Constants.CLIENT_ROLE_BROADCASTER);
        } else {
            intent.putExtra("clientRole", Constants.CLIENT_ROLE_AUDIENCE);
        }
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //make nav clickable
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        plantOnClickItems(this, item);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}