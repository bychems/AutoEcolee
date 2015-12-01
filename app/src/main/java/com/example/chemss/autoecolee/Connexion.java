package com.example.chemss.autoecolee;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.chemss.autoecolee.activity.MainActivity;
import com.example.chemss.autoecolee.activity.RegisterActivity;
import com.example.chemss.autoecolee.app.AppConfig;
import com.example.chemss.autoecolee.app.AppController;
import com.example.chemss.autoecolee.helper.SQLiteHandler;
import com.example.chemss.autoecolee.helper.SessionManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Connexion extends ActionBarActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btConnexion;
    private Button btInscription;
    private EditText etLogin;
    private EditText etMdp;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);

        btInscription=(Button)findViewById(R.id.btInscription);
        btConnexion=(Button)findViewById(R.id.btConnexion);
        final CheckBox cbMdp=(CheckBox)findViewById(R.id.cbMdp);
        etMdp=(EditText)findViewById(R.id.etMdp);
        etLogin=(EditText)findViewById(R.id.etLogin);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Connexion.this, Accueil2.class);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        btConnexion.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String login = etLogin.getText().toString().trim();
                String password = etMdp.getText().toString().trim();

                // Check for empty data in the form
                if (!login.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(login, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        btInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Connexion.this, Inscription.class);
                startActivity(intent);
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
        d2.setTitle("Mot de passe incorrecte!!");
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



        cbMdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbMdp.isChecked()==false){
                    etMdp.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else{
                    etMdp.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });


    }

    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);

                        // Launch main activity
                        Intent intent = new Intent(Connexion.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
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
                Intent intent = new Intent(Connexion.this, SuiteInscription.class);
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








}

