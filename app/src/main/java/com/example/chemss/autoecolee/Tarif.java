package com.example.chemss.autoecolee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Tarif extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarif);

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
                Intent intent = new Intent(Tarif.this, Accueil.class);
                startActivity(intent);
            }
            return true;
            case R.id.Inscription:
            {
                Intent intent = new Intent(Tarif.this, Inscription.class);
                startActivity(intent);
            }
            return true;
            case R.id.Connexion:
            {
                Intent intent = new Intent(Tarif.this, Connexion.class);
                startActivity(intent);
            }
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}