package iss.workshop.livestreamapp.interfaces;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import iss.workshop.livestreamapp.EntranceActivity;
import iss.workshop.livestreamapp.LoginActivity;
import iss.workshop.livestreamapp.R;

public interface IMenuAccess extends NavigationView.OnNavigationItemSelectedListener {

    @SuppressLint("NonConstantResourceId")
    default void plantOnClickItems(Context context, MenuItem item){

        Class<?> pageToOpen = null;

        switch(item.getItemId()) {
            case R.id.nav_home:
                pageToOpen = EntranceActivity.class;
                break;
            case R.id.nav_favorites:
                Toast.makeText(context, "Favorites", Toast.LENGTH_SHORT).show();
                pageToOpen = EntranceActivity.class;
                break;
            case R.id.nav_purchases:
                Toast.makeText(context, "Purchases", Toast.LENGTH_SHORT).show();
                pageToOpen = EntranceActivity.class;
                break;
            case R.id.nav_products:
                Toast.makeText(context, "Products", Toast.LENGTH_SHORT).show();
                pageToOpen = EntranceActivity.class;
                break;
            case R.id.nav_streams:
                Toast.makeText(context, "Streams", Toast.LENGTH_SHORT).show();
                pageToOpen = EntranceActivity.class;
                break;
            case R.id.nav_dashboard:
                Toast.makeText(context, "Dashboard", Toast.LENGTH_SHORT).show();
                pageToOpen = EntranceActivity.class;
                break;
            case R.id.nav_orders:
                Toast.makeText(context, "Orders", Toast.LENGTH_SHORT).show();
                pageToOpen = EntranceActivity.class;
                break;
            case R.id.nav_logout:
                pageToOpen = LoginActivity.class;
                break;
        }

        if (!context.getClass().getName().equals(pageToOpen.getName())){
            Intent intent = new Intent(context, pageToOpen);
            context.startActivity(intent);
        }
    }



}
