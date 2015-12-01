package com.example.chemss.autoecolee;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.chemss.autoecolee.activity.LoginActivity;
import com.example.chemss.autoecolee.activity.MainActivity;
import com.example.chemss.autoecolee.activity.RegisterActivity;
import com.example.chemss.autoecolee.app.AppConfig;
import com.example.chemss.autoecolee.app.AppController;
import com.example.chemss.autoecolee.helper.SQLiteHandler;
import com.example.chemss.autoecolee.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SuiteInscription extends ActionBarActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btAnnuler;
    private EditText etMail;
    private EditText etLogin;
    private EditText etMdp;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suiteinscription);
        etMdp=(EditText)findViewById(R.id.etMdp);
        etLogin=(EditText)findViewById(R.id.etLogin);
        etMail=(EditText)findViewById(R.id.etMail);
        EditText etMdp2=(EditText)findViewById(R.id.etMdp2);
        btAnnuler=(Button)findViewById(R.id.btRetour);
        btnRegister=(Button)findViewById(R.id.btInscri);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(SuiteInscription.this,Accueil2.class);
            startActivity(intent);
            finish();
        }

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String login = etLogin.getText().toString().trim();
                String email = etMail.getText().toString().trim();
                String password = etMdp.getText().toString().trim();

                if (!login.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    registerUser(login, email, password);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG).show();
                }
            }
        });

        final Dialog d1=new Dialog(this);
        d1.setTitle("Champs vides!!");
        Button b1=new Button(this);
        b1.setText("ok");
        d1.setContentView(b1);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                d1.dismiss();
            }
        });

        final Dialog d2=new Dialog(this);
        d2.setTitle("Mot de passe invalide");
        Button b2=new Button(this);
        b2.setText("ok");
        d2.setContentView(b2);
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                d2.dismiss();
            }
        });

        final Dialog d3=new Dialog(this);
        d3.setTitle("Demande d'inscri envoyé!!");
        Button b3=new Button(this);
        b3.setText("Ok");
        d3.setContentView(b3);
        b3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(SuiteInscription.this,Accueil.class);
                startActivity(intent);
            }
        });




        btAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuiteInscription.this, Accueil.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String name, final String email,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user.getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(SuiteInscription.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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
                Intent intent = new Intent(SuiteInscription.this, Accueil.class);
                startActivity(intent);
            }
            return true;
            case R.id.Inscription:
            {
                Intent intent = new Intent(SuiteInscription.this, Inscription.class);
                startActivity(intent);
            }
            return true;
            case R.id.Connexion:
            {
                Intent intent = new Intent(SuiteInscription.this, Connexion.class);
                startActivity(intent);
            }
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}