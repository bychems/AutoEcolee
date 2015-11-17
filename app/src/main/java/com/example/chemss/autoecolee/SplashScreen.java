package com.example.chemss.autoecolee;
        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Thread timer=new Thread(){
            public void run(){
                try{
                    sleep(3000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                } finally{
                    Intent intent=new Intent(SplashScreen.this,Accueil.class);
                    startActivity(intent);
                }
            }
        };
        timer.start();
    }


}

