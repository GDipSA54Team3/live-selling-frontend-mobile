package iss.workshop.livestreamapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.agora.rtc2.Constants;

public class EntranceActivity extends AppCompatActivity implements IStreamDetails{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);

        Button startA = (Button) findViewById(R.id.startA);
        Button watchA = (Button) findViewById(R.id.watchA);
        Button startB = (Button) findViewById(R.id.startB);
        Button watchB = (Button) findViewById(R.id.watchB);


        //start stream A as host
        startA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStreamPage("seller");
            }
        });

        //watch stream A as audience
        watchA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStreamPage("buyer");
            }
        });

        //start stream B as host
        startB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //watch stream B as audience
        watchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

    /*
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
    */


    public void openStreamPage(String role){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("channelName", "Test Channel");
        intent.putExtra("appID", getAppID());
        if (role == "seller"){
            intent.putExtra("clientRole", Constants.CLIENT_ROLE_BROADCASTER);
        } else {
            intent.putExtra("clientRole", Constants.CLIENT_ROLE_AUDIENCE);
        }

        startActivity(intent);
    }
}