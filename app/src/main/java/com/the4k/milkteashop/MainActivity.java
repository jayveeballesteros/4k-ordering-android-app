package com.the4k.milkteashop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import com.the4k.milkteashop.R;

import com.the4k.milkteashop.activity.RegisterActivity;
import com.the4k.milkteashop.fragment.CartFragment;
import com.the4k.milkteashop.fragment.GraidFragment;
import com.the4k.milkteashop.fragment.HomeFragment;
import com.the4k.milkteashop.fragment.IncrementFragment;
import com.the4k.milkteashop.fragment.PurchaseHistroyFragment;
import com.the4k.milkteashop.fragment.ReviewsFragment;
import com.the4k.milkteashop.fragment.UploadFragment;
import com.the4k.milkteashop.fragment.View_profileFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView username;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    SharedPreferences sharedPreferences;


    private static final String PREF_NAME = "ordering";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_USER_ID = "User UID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.frame_main,new HomeFragment());
        ft.commit();

        username = findViewById(R.id.username);
        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        sharedPreferences = getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);

    }



    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.





        int id = item.getItemId();

        if (id == R.id.logout){
            /*Logout code*/

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(KEY_EMAIL);
            Log.d("LoggedOut",KEY_EMAIL);
            editor.remove(KEY_USER_ID);
            Log.d("Key",firebaseUser.getUid());
            editor.apply();

            Intent intent = new Intent(this,RegisterActivity.class);
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.frame_main,new HomeFragment());
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_profile) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.frame_main,new View_profileFragment());
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_purchase){
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_main,new PurchaseHistroyFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }




        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
