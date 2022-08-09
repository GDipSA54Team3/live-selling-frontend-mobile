package iss.workshop.livestreamapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TimePicker;

import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

import iss.workshop.livestreamapp.interfaces.IMenuAccess;
import iss.workshop.livestreamapp.models.User;

public class ScheduleStreamActivity extends AppCompatActivity implements IMenuAccess {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    EditText dateTxt, timeTxt, mEName, mEDes;
    ImageView cal,clock;
    private int mDate, mMonth, mYear;
    private int mHour, mMinute;
    private User user;
    private Dialog dialog;
    ListView prodListView;
    Button AddProduct,CreateEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_stream);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

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

        dateTxt = findViewById(R.id.date);
        cal = findViewById(R.id.datePicker);

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar Cal = Calendar.getInstance();
                mDate = Cal.get(Calendar.DATE);
                mMonth = Cal.get(Calendar.MONTH);
                mYear = Cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ScheduleStreamActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        dateTxt.setText(date +"-"+month+"-"+year );
                    }
                }, mYear, mMonth, mDate);
                datePickerDialog.show();
            }
        });

        timeTxt = findViewById(R.id.time);
        clock = findViewById(R.id.timePicker);
        clock.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Initialize time picker dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ScheduleStreamActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                //Initialize hour and minutes
                                mHour = hourOfDay;
                                mMinute = minute;
                                //Initialize calendar
                                Calendar calendar = Calendar.getInstance();
                                //set hour minitues
                                calendar.set(0,0, 0, mHour, mMinute);
                                //set selected time on text view
                                timeTxt.setText(DateFormat.format("hh:mm aa", calendar));
                            }
                        },12, 0, false);
                //Displayed previous selected time
                timePickerDialog.updateTime(mHour, mMinute);
                //show dialog
                timePickerDialog.show();
            }
        });
        mEName = findViewById(R.id.eventName);

        mEDes = findViewById(R.id.eventDesc);
        AddProduct = findViewById(R.id.addProductBtn);
        CreateEvent = findViewById(R.id.createEventBtn);
        AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventName = mEName.getText().toString();
                String eventDesc = mEDes.getText().toString();
                //textEventName.setText('');
                //textEventDesc.setText('');
            }
        });
        //create the dialog here
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_custom_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        Button ok = dialog.findViewById(R.id.btn_okay);
        Button cancel = dialog.findViewById(R.id.btn_cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        CreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
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
        plantOnClickItems(this, item, user);
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