package com.the4k.milkteashop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.the4k.milkteashop.R;

public class SplashscreenActivity extends AppCompatActivity {
    ImageView logo;
    SharedPreferences preferences;
    private static final String PREF_NAME = "ordering";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_USER_ID = "User UID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        logo = findViewById(R.id.logo);


            final Intent intent = new Intent(this, LoginActivity.class);

            preferences = getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);


            Thread timer = new Thread() {
                public void run() {

                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {

                        startActivity(intent);
                        finish();
                    }
                }
            };

            timer.start();

    }
}
