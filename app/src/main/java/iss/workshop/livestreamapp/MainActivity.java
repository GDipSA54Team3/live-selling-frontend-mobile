package iss.workshop.livestreamapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.stream.Collectors;

import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;
import io.agora.rtc2.video.VideoCanvas;
import io.agora.rtc2.ChannelMediaOptions;
import iss.workshop.livestreamapp.adapters.ChStreamAdapter;
import iss.workshop.livestreamapp.adapters.ProductsListAdapter;
import iss.workshop.livestreamapp.adapters.ProductsStreamAdapter;
import iss.workshop.livestreamapp.interfaces.IStreamDetails;
import iss.workshop.livestreamapp.models.ChannelStream;
import iss.workshop.livestreamapp.models.Product;
import iss.workshop.livestreamapp.models.Stream;
import iss.workshop.livestreamapp.models.User;
import iss.workshop.livestreamapp.services.FetchStreamLog;
import iss.workshop.livestreamapp.services.ProductsApi;
import iss.workshop.livestreamapp.services.RetroFitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements IStreamDetails {

    // Fill the App ID of your project generated on Agora Console.
    private String appId; // = "813f22ea50924b43ae8488edb975d02c";
    // Fill the channel name.
    private String channelName;// = "Test ChannelStream";
    // Fill the temp token generated on Agora Console.
    private String token = "006813f22ea50924b43ae8488edb975d02cIACSwVYot3MJjOw/ZoWEFqBcwkViZje5dTy0hjwbD1QGzWV0cykAAAAAEACGukDPdf3xYgEAAQBy/fFi";
    private Stream currStream;
    private String numberOfViewers;
    private TextView streamStatus;
    private ChannelStream channel;
    private ChannelStream sellerChannel;
    private RtcEngine mRtcEngine;
    private String prevActivity;
    //audience or host
    private int clientRole;
    //current user logged in
    private User user;
    //button that shows the products
    private Button showProducts;
    private Dialog dialog;
    private ListView productsListing;
    private List<Product> channelProducts;

    //for orders
    private ProductsStreamAdapter prodStreamAdapter;

    private IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        // Listen for the remote host joining the channel to get the uid of the host.
        public void onUserJoined(int uid, int elapsed) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Call setupRemoteVideo to set the remote video view after getting uid from the onUserJoined callback.
                    setupRemoteVideo(uid);
                }
            });
        }

        @Override
        public void onRemoteVideoStats(RemoteVideoStats stats) {
            super.onRemoteVideoStats(stats);

        }

        /*
        @Override
        public void onUserOffline(int uid, int reason) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("agora","User offline, uid: " + (uid & 0xFFFFFFFFL));
                    //onRemoteUserLeft();
                }
            });
        }

         */
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting intent details
        Intent streamDetails = getIntent();
        appId = getAppID();
        channelName = streamDetails.getStringExtra("channelName");

        //streamId = streamDetails.getLongExtra("streamId", 0);
        //token = streamDetails.getStringExtra("token");
        clientRole = streamDetails.getIntExtra("clientRole", 0);
        currStream = (Stream) streamDetails.getSerializableExtra("streamObj");
        prevActivity = streamDetails.getStringExtra("calling-activity");
        user = (User) streamDetails.getSerializableExtra("user");
        channel = (ChannelStream) streamDetails.getSerializableExtra("channel");
        invokeToken(channel);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.product_list_layout);

        productsListing = dialog.findViewById(R.id.products_list);

        showProducts = findViewById(R.id.open_product_list);
        //if clientRole is buyer (audience) or seller (broadcaster)
        if(clientRole == Constants.CLIENT_ROLE_BROADCASTER){
            showProducts.setVisibility(View.INVISIBLE);
        } else {
            sellerChannel = (ChannelStream) streamDetails.getSerializableExtra("seller-stream");
            invokeToken(sellerChannel);
            RetroFitService rfServ = new RetroFitService("get-products");
            ProductsApi prodAPI = rfServ.getRetrofit().create(ProductsApi.class);
            prodAPI.getAllProductsInStore(sellerChannel.getId()).enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    channelProducts = response.body();
                    populateListView();
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {

                }
            });
        }

        showProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProductDialog();
            }
        });
        //get products from channel


        //
        //Toast.makeText(this, currStream.getName(), Toast.LENGTH_SHORT).show();
        TextView txtName = findViewById(R.id.channel_name);
        txtName.setText(channelName);

        //hide top bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        streamStatus = findViewById(R.id.stream_status);
        streamStatus.setVisibility(View.INVISIBLE);

        if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) &&
                checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID)) {
            initializeAndJoinChannel();
        }

    }

    private void populateListView() {
            prodStreamAdapter = new ProductsStreamAdapter(this, channelProducts);
            productsListing.setAdapter(prodStreamAdapter);
    }


    private static final int PERMISSION_REQ_ID = 22;
    private static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };

    private boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, requestCode);
            return false;
        }
        return true;
    }

    private void initializeAndJoinChannel() {
        try {
            RtcEngineConfig config = new RtcEngineConfig();
            config.mContext = getBaseContext();
            config.mAppId = appId;
            config.mEventHandler = mRtcEventHandler;
            mRtcEngine = RtcEngine.create(config);
        } catch (Exception e) {
            throw new RuntimeException("Check the error.");
        }
        // By default, video is disabled, and you need to call enableVideo to start a video stream.
        mRtcEngine.enableVideo();
        // Start local preview.
        mRtcEngine.startPreview();

        //mRtcEventHandler.onUserJoined();

        FrameLayout container = findViewById(R.id.local_video_view_container);
        SurfaceView surfaceView = new SurfaceView (getBaseContext());
        container.addView(surfaceView);

        ChannelMediaOptions options = new ChannelMediaOptions();
        // Set the client role as BROADCASTER or AUDIENCE according to the scenario.
        options.clientRoleType = clientRole;
        // For the Interactive Live Streaming Standard scenario, the user level of an audience member needs to be AUDIENCE_LATENCY_LEVEL_LOW_LATENCY.
        options.audienceLatencyLevel = Constants.AUDIENCE_LATENCY_LEVEL_LOW_LATENCY;
        // For a live streaming scenario, set the channel profile as BROADCASTING.
        options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING;

        if (options.clientRoleType == Constants.CLIENT_ROLE_BROADCASTER){
            mRtcEngine.setupLocalVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, 0));
            //start fetching of stream log
            Intent intent = new Intent(MainActivity.this, FetchStreamLog.class);
            intent.setAction("send_messages");
            intent.putExtra("seller", user);
            intent.putExtra("stream", currStream);
            intent.putExtra("duration", 5);
            startService(intent);

        } else {
            boolean hostIsInside = true;
            if (!hostIsInside){
                streamStatus.setText("Stream is not ongoing. Please wait for the next stream.");
                streamStatus.setVisibility(View.VISIBLE);
            }
            //check if host is inside. if not, run the

        }
        // Pass the SurfaceView object to Agora so that it renders the local video.

        // Join the channel with a temp token.
        // You need to specify the user ID yourself, and ensure that it is unique in the channel.
        mRtcEngine.joinChannel(token, channelName, 0, options);
    }

    private void setupRemoteVideo(int uid) {
        FrameLayout container = findViewById(R.id.local_video_view_container);
        SurfaceView surfaceView = new SurfaceView (getBaseContext());
        surfaceView.setZOrderMediaOverlay(true);
        container.addView(surfaceView);
        mRtcEngine.setupRemoteVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid));
    }

    private void openProductDialog(){

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (getResources().getDisplayMetrics().heightPixels*0.60));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(MainActivity.this, FetchStreamLog.class);
                stopService(intent);
                mRtcEngine.stopPreview();
                mRtcEngine.leaveChannel();
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}