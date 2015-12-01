package com.example.chemss.autoecolee;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Accueil extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);

        Button btTarif=(Button)findViewById(R.id.btTarif);
        btTarif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accueil.this, Tarif.class);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Accueil:
            {
                Intent intent = new Intent(Accueil.this, Accueil.class);
                startActivity(intent);
            }
                return true;
            case R.id.Inscription:
            {
                Intent intent = new Intent(Accueil.this, SuiteInscription.class);
                startActivity(intent);
            }
                return true;
            case R.id.Connexion:
            {
                Intent intent = new Intent(Accueil.this, Connexion.class);
                startActivity(intent);
            }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}