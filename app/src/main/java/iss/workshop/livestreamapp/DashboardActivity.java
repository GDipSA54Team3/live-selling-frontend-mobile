package iss.workshop.livestreamapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import iss.workshop.livestreamapp.models.Stream;
import iss.workshop.livestreamapp.models.User;
import iss.workshop.livestreamapp.services.DashboardApi;
import iss.workshop.livestreamapp.services.RetroFitService;
import iss.workshop.livestreamapp.services.StreamsApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private User user;
    private RetroFitService rfServ;
    private DashboardApi dashboardApi;
    String[] userlikes = {""};
    String[] othersLikes = {""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        rfServ = new RetroFitService("dashboard");
        dashboardApi = rfServ.getRetrofit().create(DashboardApi.class);
        final Handler handler = new Handler();
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        setUserRating();
        setPopularityView();
        setPengingOrders();


        // Getting all the charts
        setPopularityChart();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setTimeSeriesChart();
            }
        }, 1000);
        setPopularityChart();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getOrderByTimePeriodChart();
            }
        }, 2000);

    }
    public void setUserRating() {
        dashboardApi.getUserAverageRating(user.getId()).enqueue(new Callback<String>() {
            TextView ratingView = findViewById(R.id.rating_view);
            RatingBar ratingBar = findViewById(R.id.rating_bar_dash);
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ratingView.setText(response.body()+"/5");
                ratingBar.setRating(Float.parseFloat(response.body()));
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setPopularityView() {

        TextView popularityView = findViewById(R.id.popularity_view);
        dashboardApi.getUserAverageLikes(user.getId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                userlikes[0] = (response.body());
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        dashboardApi.getAverageStreamLikes().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                othersLikes[0] = (response.body());
             }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setPopularity(userlikes, othersLikes);
            }
        }, 1000);
    }
    public void setPopularity(String[] userslikes, String[]  othersLikes) {
        Integer userlikeCount = Integer.parseInt(userslikes[0]);
        Integer otherlikeCount = Integer.parseInt(othersLikes[0]);
        TextView popularityView = findViewById(R.id.popularity_view);
        if (userlikeCount == 0 ) {
            popularityView.setText("You haven't received any reactions yet. "+
                    "Good luck on your next stream!");
        }
        else if (userlikeCount < otherlikeCount && userlikeCount != 0) {
            popularityView.setText("You are getting less Hearts than other streamers. "+
                    "Try to improve. Good luck!");
        }
        else if (userlikeCount < otherlikeCount && userlikeCount != 0) {
            popularityView.setText("You are getting less Hearts than other streamers. "+
                    "Try to improve. Good luck!");
        }
        else if (userlikeCount == otherlikeCount && userlikeCount != 0) {
            popularityView.setText("You are getting same number of Hearts as other streamers. "+
                    "Good job!. Check if you can improve further");
        }
        else if (userlikeCount > otherlikeCount) {
            popularityView.setText("You are getting more Hearts than Other Streamers "+
                    "Good job!. Check if you can improve even further");
        }
    }

    public void setTimeSeriesChart(){
        String userId = user.getId();
        String imageUri = "http://10.0.2.2:5000/charts?name=movavg";
        ImageView timeSeries = (ImageView) findViewById(R.id.time_series_chart);

        Picasso.with(this)
                .load(imageUri)
                .resize(600, 200)
                .centerInside()
                .into(timeSeries);
    }
    public void setPopularityChart(){
        String userId = user.getId();
        String imageUri = "http://10.0.2.2:5000/charts?name=popchart&userid="+userId;
        ImageView popularityChart = (ImageView) findViewById(R.id.pop_chart);
        Picasso.with(this)
                .load(imageUri)
                .resize(600, 200)
                .centerInside()
                .into(popularityChart);
    }
    public void getOrderByTimePeriodChart(){
        String imageUri = "http://10.0.2.2:5000/charts?name=bytime";
        ImageView timePeriodChart = (ImageView) findViewById(R.id.time_period_chart);
        Picasso.with(this)
                .load(imageUri)
                .resize(600, 200)
                .centerInside()
                .into(timePeriodChart);
    }
    public void setPengingOrders(){
        TextView pendingOrderView = findViewById(R.id.pending_order_count);
        dashboardApi.getPengingOrders(user.getId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                pendingOrderView.setText(response.body());
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}