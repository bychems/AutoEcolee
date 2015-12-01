package com.example.chemss.autoecolee;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Inscription extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);
        final EditText etNom=(EditText)findViewById(R.id.etNom);
        final EditText etPrenom=(EditText)findViewById(R.id.etPrenom);
        final EditText etCin=(EditText)findViewById(R.id.etCin);
        final EditText etNaissance=(EditText)findViewById(R.id.etNaissance);
        final EditText etMail=(EditText)findViewById(R.id.etMail);

        final Dialog d=new Dialog(this);
        d.setTitle("Champs vides!!");
        Button b1=new Button(this);
        b1.setText("ok");
        d.setContentView(b1);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                d.dismiss();
            }
        });

        Button btSuite=(Button)findViewById(R.id.btSuite);
        btSuite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });String verifNom=etNom.getText().toString();
        String verifPrenom=etPrenom.getText().toString();
        String verifCin=etCin.getText().toString();
        String verifNaissance=etNaissance.getText().toString();
        String verifMail=etMail.getText().toString();
        if((verifNom.contentEquals(""))||(verifPrenom.contentEquals(""))||(verifCin.contentEquals(""))||(verifNaissance.contentEquals(""))||(verifMail.contentEquals(""))) {
            d.show();
        }
        else {
            Intent intent = new Intent(Inscription.this, SuiteInscription.class);
            startActivity(intent);
        }

        Button btAnnuler=(Button)findViewById(R.id.btAnnuler);
        btAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inscription.this, Accueil.class);
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
                Intent intent = new Intent(Inscription.this, Accueil.class);
                startActivity(intent);
            }
            return true;
            case R.id.Inscription:
            {
                Intent intent = new Intent(Inscription.this, Inscription.class);
                startActivity(intent);
            }
            return true;
            case R.id.Connexion:
            {
                Intent intent = new Intent(Inscription.this, Connexion.class);
                startActivity(intent);
            }
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}