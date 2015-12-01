package com.example.chemss.autoecolee.activity;

/**
 * Created by chemss on 30/11/2015.
 */
import com.example.chemss.autoecolee.Accueil2;
import com.example.chemss.autoecolee.R;
import com.example.chemss.autoecolee.helper.SQLiteHandler;
import com.example.chemss.autoecolee.helper.SessionManager;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

        public class MainActivity extends Activity {

            private TextView txtName;
            private TextView txtEmail;


            private SQLiteHandler db;
            private SessionManager session;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                Thread timer=new Thread(){
                    public void run(){
                        try{
                            sleep(3000);
                        } catch(InterruptedException e){
                            e.printStackTrace();
                        } finally{
                            Intent intent=new Intent(MainActivity.this,Accueil2.class);
                            startActivity(intent);
                        }
                    }
                };
                timer.start();

                txtName = (TextView) findViewById(R.id.name);
                txtEmail = (TextView) findViewById(R.id.email);


                // SqLite database handler
                db = new SQLiteHandler(getApplicationContext());

                // session manager
                session = new SessionManager(getApplicationContext());

                if (!session.isLoggedIn()) {
                    logoutUser();
                }

                // Fetching user details from sqlite
                HashMap<String, String> user = db.getUserDetails();

                String name = user.get("name");
                String email = user.get("email");

                // Displaying the user details on the screen
                txtName.setText(name);
                txtEmail.setText(email);

            }

            /**
             * Logging out the user. Will set isLoggedIn flag to false in shared
             * preferences Clears the user data from sqlite users table
             * */
            private void logoutUser() {
                session.setLogin(false);

                db.deleteUsers();

                // Launching the login activity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }


        }