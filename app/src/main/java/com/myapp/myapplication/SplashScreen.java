package com.myapp.myapplication;

/**
 * Created by Rajesh on 25-08-2017.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashScreen extends Activity {

    private static int SPLASH_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                //Check network connectivity and server connectivity here and proceed
                Intent mainActivity = new Intent(getApplicationContext(),com.myapp.myapplication.MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        }, SPLASH_TIME);

    }
}
