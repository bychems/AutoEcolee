package com.example.chemss.autoecolee;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class Reservation extends ActionBarActivity {

    Spinner spJours;
    Spinner spHeures;
    Button btReserver;
    Button btAnnulerRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);

        spJours=(Spinner)findViewById(R.id.spJours);

        ArrayAdapter<CharSequence> ar= ArrayAdapter.createFromResource(this, R.array.jour,android.R.layout.simple_list_item_1);
        ar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spJours.setAdapter(ar);

        spHeures=(Spinner)findViewById(R.id.spHeure);

        ArrayAdapter<CharSequence> a= ArrayAdapter.createFromResource(this, R.array.heure,android.R.layout.simple_list_item_1);
        a.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spHeures.setAdapter(a);

        btAnnulerRes=(Button)findViewById(R.id.btAnnulerRes);
        btAnnulerRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Reservation.this,Accueil2.class);
                startActivity(intent);
            }
        });

        btReserver=(Button)findViewById(R.id.btReserver);
        /*btReserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Accueil2:
            {
                Intent intent = new Intent(Reservation.this, Accueil2.class);
                startActivity(intent);
            }
            return true;
            case R.id.Profil:
            {
                Intent intent = new Intent(Reservation.this, Profil.class);
                startActivity(intent);
            }
            return true;
            case R.id.Deconnexion:
            {
                final Dialog d=new Dialog(this);
                d.setTitle("Etes vous sur?");
                Button b1=new Button(this);
                b1.setText("Oui");
                d.setContentView(b1);

                b1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent intent=new Intent(Reservation.this,Connexion.class);
                        startActivity(intent);
                    }
                });

                d.show();
            }
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
