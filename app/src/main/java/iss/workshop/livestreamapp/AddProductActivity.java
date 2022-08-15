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

import java.util.List;
import java.util.stream.Collectors;

import iss.workshop.livestreamapp.helpers.ProductCategories;
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
    String [] items = ProductCategories.names();
    AutoCompleteTextView autoCompleteTxt;
    TextInputLayout textInputLayout;

    //Add Product
    EditText productName, productPrice, productDescription;
    Button addProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get user
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        //get channel
        channel = (ChannelStream) intent.getSerializableExtra("channel");

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
        qtyValue = findViewById(R.id.quantity);

        //Add Product
        productName = findViewById(R.id.pName);
        productPrice = findViewById(R.id.pPrice);
        productDescription = findViewById(R.id.pDesc);
        addProduct = findViewById(R.id.createProduct);

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent response = new Intent();
                response.putExtra("product_name", productName.getText().toString());
                response.putExtra("product_price", Double.parseDouble(productPrice.getText().toString()));
                response.putExtra("product_desc", productDescription.getText().toString());
                response.putExtra("category", fetchCategory(autoCompleteTxt.getText().toString()));
                response.putExtra("quantity", Integer.parseInt(qtyValue.getText().toString()));
                //response.putExtra(“computedSum", 100);
                setResult(RESULT_OK, response);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //make nav clickable
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    @Override
    public void setupSidebarMenu() {
        return;
    }


    public ProductCategories fetchCategory (String strCategory){
        ProductCategories category = null;

        switch(strCategory){
            case("HEALTH"):
                category = ProductCategories.HEALTH;
                break;
            case("FURNITURES"):
                category = ProductCategories.FURNITURES;
                break;
            case("APPLIANCES"):
                category = ProductCategories.APPLIANCES;
                break;
            case("BABY"):
                category = ProductCategories.BABY;
                break;
            case("CLOTHING"):
                category = ProductCategories.CLOTHING;
                break;
            case("FOOD"):
                category = ProductCategories.FOOD;
                break;
            case("GROCERIES"):
                category = ProductCategories.GROCERIES;
                break;
            case("SPORTS"):
                category = ProductCategories.SPORTS;
                break;
            case("TECHNOLOGY"):
                category = ProductCategories.TECHNOLOGY;
                break;
            default:
                category = ProductCategories.OTHERS;
                break;
        }

        return category;
    }
}