package com.example.twitterminer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.twitterminer.database.AppDatabase;
import com.example.twitterminer.entities.Usuario;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.twitterminer.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    Button telaInicialButton;
    TextView textLogin;
    TextView textSenha;
    AppDatabase db;
    int duration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textLogin = findViewById(R.id.editTextLogin);
        textSenha = findViewById(R.id.editTextPassword);
        telaInicialButton = findViewById(R.id.buttonEntrar);

         db = AppDatabase.getDatabase(getApplicationContext());


        telaInicialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = textLogin.getText().toString();
                String senha = textSenha.getText().toString();

                Context context = getApplicationContext();

                AppDatabase.databaseWriteExecutor.execute(() -> {
                    Usuario usuario = db.usuarioDao().findByLogin(login);
                    if (usuario != null) {
                        if (usuario.senha.equals(senha)) {
                            Intent intent = new Intent(context, com.example.twitterminer.TelaInicialActivity.class);
                            startActivity(intent);
                        } else {
                            ContextCompat.getMainExecutor(context).execute(()  -> {
                                Toast toast = Toast.makeText(context, "Senha incorreta", duration);
                                toast.show();
                            });
                        }
                    } else {
                        ContextCompat.getMainExecutor(context).execute(()  -> {
                            Toast toast = Toast.makeText(context, "Usuário não cadastrado", duration);
                            toast.show();
                        });
                    }
                });
            }
        });
    }
}