package com.example.chemss.autoecolee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by chemss on 23/11/2015.
 */
public class Accueil2 extends ActionBarActivity{

    TextView tvProfil;
    TextView tvReserver;
    TextView tvReservation;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil2);

        tvProfil=(TextView)findViewById(R.id.tvProfil);
        /*tvProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accueil2.this, Profil.class);
                startActivity(intent);
            }
        });*/

        tvReserver=(TextView)findViewById(R.id.tvReserver);
        tvReserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Accueil2.this,Reservation.class);
                startActivity(intent);
            }
        });

        tvReservation=(TextView)findViewById(R.id.tvResevation);
       /* tvReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Accueil2.this,Reservation.class);
                startActivity(intent);
            }
        });*/

    }
}
