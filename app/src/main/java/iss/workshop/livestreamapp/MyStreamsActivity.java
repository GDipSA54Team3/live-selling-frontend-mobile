package iss.workshop.livestreamapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.agora.rtc2.Constants;
import iss.workshop.livestreamapp.adapters.ChStreamAdapter;
import iss.workshop.livestreamapp.interfaces.IMenuAccess;
import iss.workshop.livestreamapp.interfaces.IStreamDetails;
import iss.workshop.livestreamapp.models.ChannelStream;
import iss.workshop.livestreamapp.models.Stream;
import iss.workshop.livestreamapp.models.User;
import iss.workshop.livestreamapp.services.RetroFitService;
import iss.workshop.livestreamapp.services.StreamsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyStreamsActivity extends AppCompatActivity implements IMenuAccess, IStreamDetails {

    private User user;
    private ChannelStream channel;
    private Stream currStream;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ChStreamAdapter streamAdapter;
    private ListView listOfStreams;
    private Button btnStartStream;
    private Button btnCreateStream;
    private ActivityResultLauncher<Intent> rlCreateStream;
    private RetroFitService rfServ;
    private StreamsApi streamAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_streams);

        //get user
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        //get channel
        channel = (ChannelStream) intent.getSerializableExtra("channel");
        invokeToken(channel);
        setupSidebarMenu();
        //TextView txtChannelName = findViewById(R.id.channel_name);
        //txtChannelName.setText(channel.getName());

        //populate stream list
        rfServ = new RetroFitService("stream");
        streamAPI = rfServ.getRetrofit().create(StreamsApi.class);
        listOfStreams = findViewById(R.id.my_streams_list);

        streamAPI.getAllUserStreams(user.getId()).enqueue(new Callback<List<Stream>>() {
            @Override
            public void onResponse(Call<List<Stream>> call, Response<List<Stream>> response) {
                populateMyStreamList(response.body());
            }

            @Override
            public void onFailure(Call<List<Stream>> call, Throwable t) {
                Toast.makeText(MyStreamsActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        rlCreateStream = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                Intent streamDetails = result.getData();

                Stream newStream = new Stream();
                newStream.setTitle(streamDetails.getStringExtra("title"));

                LocalDateTime newDate = (LocalDateTime) streamDetails.getSerializableExtra("schedule");
                //newStream.setSchedule(newDate);
                newStream.setTempSchedule(newDate.toString());

                rfServ = new RetroFitService("stream");
                streamAPI = rfServ.getRetrofit().create(StreamsApi.class);
                streamAPI.addNewStream(newStream, user.getId()).enqueue(new Callback<Stream>() {
                            @Override
                            public void onResponse(Call<Stream> call, Response<Stream> response) {
                                if(response.code() == 400){
                                    Toast.makeText(MyStreamsActivity.this,
                                            "Stream was not saved. Try again",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MyStreamsActivity.this,
                                            "Stream Title: " + response.body() + " has been added!",
                                            Toast.LENGTH_SHORT).show();

                                    streamAdapter.notifyDataSetChanged();
                                }

                            }

                            @Override
                            public void onFailure(Call<Stream> call, Throwable t) {
                                Toast.makeText(MyStreamsActivity.this, "Stream was not saved. Try again", Toast.LENGTH_SHORT).show();
                            }
                });

            }
        });

        btnCreateStream = findViewById(R.id.btn_create_stream);
        btnCreateStream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(MyStreamsActivity.this, ScheduleStreamActivity.class);
                    intent.putExtra("user", user);
                    intent.putExtra("channel", channel);
                    rlCreateStream.launch(intent);
            }
        });

        //set on click listener to start stream
        btnStartStream = findViewById(R.id.btn_start_now);
        btnStartStream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyStreamsActivity.this, MainActivity.class);
                intent.putExtra("channelName", channel.getName());
                intent.putExtra("appID", getAppID());
                intent.putExtra("streamObj", new Stream());
                intent.putExtra("user", user);
                intent.putExtra("clientRole", Constants.CLIENT_ROLE_BROADCASTER);
                startActivity(intent);
            }
        });

    }

    private void populateMyStreamList(List<Stream> body) {
        streamAdapter = new ChStreamAdapter(this, body, true);
        listOfStreams.setAdapter(streamAdapter);
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
        plantOnClickItems(this, item, user, channel);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setupSidebarMenu() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

}