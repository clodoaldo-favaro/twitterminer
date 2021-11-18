package com.example.twitterminer;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.twitterminer.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    Button telaInicialButton;
    TextView textLogin;
    TextView textSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textLogin = findViewById(R.id.editTextLogin);
        textSenha = findViewById(R.id.editTextPassword);
        telaInicialButton = findViewById(R.id.buttonEntrar);


        telaInicialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = textLogin.getText().toString();
                String senha = textSenha.getText().toString();

                if (true) {
                    Intent intent = new Intent(getApplicationContext(), com.example.twitterminer.TelaInicialActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}