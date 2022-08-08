package iss.workshop.livestreamapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
import iss.workshop.livestreamapp.interfaces.ISessionUser;
import iss.workshop.livestreamapp.interfaces.IStreamDetails;
import iss.workshop.livestreamapp.models.ChannelStream;
import iss.workshop.livestreamapp.models.Stream;
import iss.workshop.livestreamapp.models.User;
import iss.workshop.livestreamapp.services.RetroFitService;
import iss.workshop.livestreamapp.services.StreamsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntranceActivity extends AppCompatActivity implements IStreamDetails, IMenuAccess, ISessionUser {

    private ListView listOfStreams;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ChannelStream channelStream;
    private Stream currStream;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);

        Button startA = findViewById(R.id.startA);
        Button startB = findViewById(R.id.startB);

        //checkUser method
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        SharedPreferences sPref = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        if (isValidated(sPref, user.getUsername(), user.getPassword())){
            //unique to entrance activity
            TextView welcomeUser = findViewById(R.id.welcome_user);
            welcomeUser.setText("Welcome " + user.getFirstName() + "!");
        } else {
            logOut(sPref, this);
        }

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

        RetroFitService rfServ = new RetroFitService("stream");
        StreamsApi streamAPI = rfServ.getRetrofit().create(StreamsApi.class);

        streamAPI.getAllStreams().enqueue(new Callback<List<Stream>>() {
            @Override
            public void onResponse(Call<List<Stream>> call, Response<List<Stream>> response)
            {
                System.out.println(response.isSuccessful());
                System.out.println(response.body());
                populateStreamList(response.body());
            }

            @Override
            public void onFailure(Call<List<Stream>> call, Throwable t) {
                Toast.makeText(EntranceActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        //start stream A as host
        startA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStreamPage("seller", channelStream, new Stream());
            }
        });

        //start stream B as host
        startB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStreamPage("seller", channelStream, new Stream());
            }
        });
    }

    private void populateStreamList(List<Stream> body) {
        ChStreamAdapter streamAdapter = new ChStreamAdapter(this, body);
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

                currStream = (Stream) streamAdapter.getItem(i);
                channelStream = currStream.getChannelStream();
                Toast.makeText(EntranceActivity.this, currStream.getTitle(), Toast.LENGTH_SHORT).show();

                openStreamPage("buyer", channelStream, currStream);
            }

        });

    }


    public void openStreamPage(String role, ChannelStream channelStream, Stream currStream){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("channelName", channelStream.getName());
        //intent.putExtra("token", "006813f22ea50924b43ae8488edb975d02cIACSwVYot3MJjOw/ZoWEFqBcwkViZje5dTy0hjwbD1QGzWV0cykAAAAAEACGukDPdf3xYgEAAQBy/fFi");
        intent.putExtra("appID", getAppID());
        //intent.putExtra("streamID", streamId);
        intent.putExtra("streamObj", currStream);
        intent.putExtra("user", user);
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