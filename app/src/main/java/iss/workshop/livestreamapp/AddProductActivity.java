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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import iss.workshop.livestreamapp.interfaces.IMenuAccess;
import iss.workshop.livestreamapp.interfaces.IStreamDetails;
import iss.workshop.livestreamapp.models.ChannelStream;
import iss.workshop.livestreamapp.models.Stream;
import iss.workshop.livestreamapp.models.User;

public class AddProductActivity extends AppCompatActivity implements IMenuAccess, IStreamDetails {

    private User user;
    private ChannelStream channel;
    private Stream currStream;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    //in, de qty
   private TextView qtyValue;
   int count = 0;
   private ImageView up, down;

   //drop down category
    String [] items = {"CLOTHING", "FOOD", "APPLIANCES", "FURNITURE",
            "technology", "BABY", "HEALTH", "OTHERS", "SPORT", "GROCERIES"};
    AutoCompleteTextView autoCompleteTxt;
    TextInputLayout textInputLayout;

    //Add Product
    EditText productName, productPrice, productDescription;
    Button addProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        setupSidebarMenu();

        //get user
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        //get channel
        channel = generateChannel(user, this);

        //dropdown category
        textInputLayout = findViewById(R.id.menu_drop);
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(this,R.layout.list_category, items);
        autoCompleteTxt.setAdapter(itemAdapter);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //textView.setText((String)parents.getItemPosition(position));

                //Toast.makeText(getApplicationContext(), "Item"+ item, Toast.LENGTH_SHORT).show();
            }
        });
        //in, de qty
        qtyValue = findViewById(R.id.value);
        up = findViewById(R.id.dropUp);
        down = findViewById(R.id.dropDown);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                qtyValue.setText(count);}
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //not to decrease if it zero
                if(count < 0) { count =0;}
                else count--;
                qtyValue.setText(""+count);}
        });
        //Add Product
        productName = findViewById(R.id.pName);
        productPrice = findViewById(R.id.pPrice);
        productDescription = findViewById(R.id.pDesc);
        addProduct = findViewById(R.id.createProduct);
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