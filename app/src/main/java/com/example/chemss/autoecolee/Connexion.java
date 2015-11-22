package com.example.chemss.autoecolee;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.BatchUpdateException;

public class Connexion extends ActionBarActivity {

    private EditText login,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);

        Button btInscription=(Button)findViewById(R.id.btInscription);
        Button btConnexion=(Button)findViewById(R.id.btConnexion);



        btInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Connexion.this, Inscription.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Accueil:
            {
                Intent intent = new Intent(Connexion.this, Accueil.class);
                startActivity(intent);
            }
            return true;
            case R.id.Inscription:
            {
                Intent intent = new Intent(Connexion.this, Inscription.class);
                startActivity(intent);
            }
            return true;
            case R.id.Connexion:
            {
                Intent intent = new Intent(Connexion.this, Connexion.class);
                startActivity(intent);
            }
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void login(View view){
        String username =login.getText().toString();
        String pass =password.getText().toString();
        new SigninActivity(this).execute(username,pass);

    }


}

