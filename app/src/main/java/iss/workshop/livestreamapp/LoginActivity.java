package iss.workshop.livestreamapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import iss.workshop.livestreamapp.interfaces.ISessionUser;
import iss.workshop.livestreamapp.models.User;

public class LoginActivity extends AppCompatActivity implements ISessionUser {

    private Button btnLogin;
    TextView newAccount;
    private SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPage();
            }
        });
        newAccount = findViewById(R.id.createNewAccount);
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        sPref = getSharedPreferences("userDetails", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sPref.edit();
        editor
                .putString("username", "testUser")
                .putString("password", "password")
                .apply();

    }

    public void openPage (){
        TextView txtUsername = findViewById(R.id.txtUsername);
        TextView txtPassword = findViewById(R.id.txtPassword);

        if (isValidated(sPref, txtUsername.getText().toString(), txtPassword.getText().toString())){
            User user = new User(txtUsername.getText().toString(), txtPassword.getText().toString());
            Intent intent = new Intent(this, EntranceActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Username and Password invalid. Please try again.", Toast.LENGTH_SHORT).show();
        }



    }
}