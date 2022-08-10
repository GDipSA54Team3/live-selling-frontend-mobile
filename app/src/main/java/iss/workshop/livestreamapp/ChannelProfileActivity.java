package iss.workshop.livestreamapp;

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
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.stream.Collectors;

import iss.workshop.livestreamapp.interfaces.IMenuAccess;
import iss.workshop.livestreamapp.interfaces.IStreamDetails;
import iss.workshop.livestreamapp.models.ChannelStream;
import iss.workshop.livestreamapp.models.Rating;
import iss.workshop.livestreamapp.models.Stream;
import iss.workshop.livestreamapp.models.User;

public class ChannelProfileActivity extends AppCompatActivity implements IMenuAccess, IStreamDetails {

    private User user;
    private ChannelStream channel;
    private Stream currStream;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private RatingBar ratingBar;
    private Button btnSubmit;
    private TextView rateCount, showRating;
    EditText review;
    float rateValue; String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_profile);

        setupSidebarMenu();

        //get user
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        //get channel

        channel = (ChannelStream) intent.getSerializableExtra("channel");
        invokeToken(channel);

        //rating bar
        rateCount = findViewById(R.id.rate_Count);
        ratingBar = findViewById(R.id.rating_bar);
        btnSubmit = findViewById(R.id.rating_submit);
        review = findViewById(R.id.write_Review);
        showRating = findViewById(R.id.showRating);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rateValue = ratingBar.getRating();

                if(rateValue <=1 && rateValue>0)
                    rateCount.setText("Bad" + rateValue + "/5");
                else if(rateValue <=2 && rateValue>1)
                    rateCount.setText("Ok" + rateValue + "/5");
                else if(rateValue <=3 && rateValue>2)
                    rateCount.setText("Good" + rateValue + "/5");
                else if(rateValue <=4 && rateValue>3)
                    rateCount.setText("Very Good" + rateValue + "/5");
                else if(rateValue <=5 && rateValue>4)
                    rateCount.setText("Best" + rateValue + "/5");
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = rateCount.getText().toString();
                showRating.setText("Your Rating: \n"+ temp+ "\n"+ review.getText());
                review.setText("");
                ratingBar.setRating(0);
                rateCount.setText("");
            }
        });
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