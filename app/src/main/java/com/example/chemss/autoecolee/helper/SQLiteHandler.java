package com.example.chemss.autoecolee.helper;

/**
 * Created by chemss on 30/11/2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

        public class SQLiteHandler extends SQLiteOpenHelper {

            private static final String TAG = SQLiteHandler.class.getSimpleName();

            // All Static variables
            // Database Version
            private static final int DATABASE_VERSION = 1;

            // Database Name
            private static final String DATABASE_NAME = "android_api";

            // Login table name
            private static final String TABLE_CLIENT = "client";
            // Login Table Columns names
            private static final String NOM_CLIENT = "nom";
            private static final String PRENOM_CLIENT = "prenom";
            private static final String CIN_CLIENT = "cin";
            private static final String DATE_CLIENT = "date";
            private static final String EMAIL_CLIENT = "mail";
            private static final String LOGIN_CLIENT = "login";
            private static final String PASSWORD_CLIENT = "password";

            // Reservation table name
            private static final String TABLE_SEANCE = "seance";
            // Reservation Table Columns names
            private static final String ID_SEANCE = "id";
            private static final String JOUR_SEANCE = "jour";
            private static final String SEMAINE_SEANCE = "semaine";
            private static final String HEURE_SEANCE = "heure";
            private static final String TYPE_SEANCE = "type";
            private static final String CLIENT_SEANCE = "nom_client";

            public SQLiteHandler(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

            // Creating Tables
            @Override
            public void onCreate(SQLiteDatabase db) {
                String CREATE_CLIENT_TABLE = "CREATE TABLE " + TABLE_CLIENT + "("
                        + CIN_CLIENT + " INTEGER PRIMARY KEY," + NOM_CLIENT + " TEXT,"
                        + PRENOM_CLIENT + " TEXT UNIQUE," + EMAIL_CLIENT + " TEXT,"
                        + DATE_CLIENT + " TEXT" + LOGIN_CLIENT + " TEXT" + PASSWORD_CLIENT + " TEXT" + ")";

                String CREATE_RESERVATION_TABLE = "CREATE TABLE " + TABLE_SEANCE + "("
                        + ID_SEANCE + " INTEGER PRIMARY KEY," + JOUR_SEANCE + " TEXT,"
                        + SEMAINE_SEANCE + " INTEGER," + HEURE_SEANCE + " INTEGER,"
                        + TYPE_SEANCE + " TEXT" + CLIENT_SEANCE + " TEXT" + ")";

                db.execSQL(CREATE_CLIENT_TABLE);
                db.execSQL(CREATE_RESERVATION_TABLE);

                Log.d(TAG, "Tables dans la base crées!");
            }

            // Upgrading database
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // Drop older table if existed
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEANCE);

                // Create tables again
                onCreate(db);
            }

            /**
             * Storing client details in database
             * */
            public void addClient(String nom, String prenom, int cin, String date, String mail, String login, String password) {
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(CIN_CLIENT, cin); // Name
                values.put(NOM_CLIENT, nom); // Email
                values.put(PRENOM_CLIENT, prenom); // Email
                values.put(DATE_CLIENT, date); // Created At
                values.put(EMAIL_CLIENT, mail); // Created At
                values.put(LOGIN_CLIENT, login); // Created At
                values.put(PASSWORD_CLIENT, password); // Created At

                // Inserting Row
                long id = db.insert(TABLE_CLIENT, null, values);
                db.close(); // Closing database connection

                Log.d(TAG, "Nouveau client inséré dans sqlite: " + id);
            }

            /**
             * Storing SEANCE details in database
             * */
            public void addSeance(String jour, int semaine, int heure, String type, String nom_client) {
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(JOUR_SEANCE,jour); // Jour séance
                values.put(SEMAINE_SEANCE, semaine); // Semaine séance
                values.put(HEURE_SEANCE, heure); // Heure séance
                values.put(TYPE_SEANCE, type); // Type séance
                values.put(CLIENT_SEANCE, nom_client);// Nom Client

                // Inserting Row
                long id = db.insert(TABLE_SEANCE, null, values);
                db.close(); // Closing database connection

                Log.d(TAG, "New reservation inserted into sqlite: " + id);
            }


            /**
             * Getting client data from database
             * */
            public HashMap<String, String> getClientDetails() {
                HashMap<String, String> client = new HashMap<String, String>();
                String selectQuery = "SELECT  * FROM " + TABLE_CLIENT;

                SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                // Move to first row
                cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    client.put("nom", cursor.getString(1));
                    client.put("prenom", cursor.getString(2));
                    client.put("date", cursor.getString(3));
                    client.put("mail", cursor.getString(4));
                    client.put("login", cursor.getString(4));
                    client.put("password", cursor.getString(4));
                }
                cursor.close();
                db.close();
                // return client
                Log.d(TAG, "Fetching client from Sqlite: " + client.toString());

                return client;
            }

            public HashMap<String, String> getSeanceDetails() {
                HashMap<String, String> seance = new HashMap<String, String>();
                String selectQuery = "SELECT  * FROM " + TABLE_SEANCE;

                SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                // Move to first row
                cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    seance.put("jour", cursor.getString(1));
                    seance.put("semaine", cursor.getString(2));
                    seance.put("heure", cursor.getString(3));
                    seance.put("type", cursor.getString(4));
                    seance.put("nom_client", cursor.getString(4));
                }
                cursor.close();
                db.close();
                // return client
                Log.d(TAG, "Fetching seance from Sqlite: " + seance.toString());

                return seance;
            }

            /**
             * Re crate database Delete all tables and create them again
             * */
            public void deleteClient() {
                SQLiteDatabase db = this.getWritableDatabase();
                // Delete All Rows
                db.delete(TABLE_CLIENT, null, null);
                db.close();

                Log.d(TAG, "Deleted all client info from sqlite");
            }

        }

