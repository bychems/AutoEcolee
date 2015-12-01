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

public class Profil extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);
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
                Intent intent = new Intent(Profil.this, Accueil2.class);
                startActivity(intent);
            }
            return true;
            case R.id.Profil:
            {
                Intent intent = new Intent(Profil.this, Profil.class);
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
                        Intent intent=new Intent(Profil.this,Connexion.class);
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
