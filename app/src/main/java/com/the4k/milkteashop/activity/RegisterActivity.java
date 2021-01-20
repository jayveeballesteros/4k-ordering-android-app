package com.the4k.milkteashop.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.the4k.milkteashop.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

import com.the4k.milkteashop.R;

public class RegisterActivity extends AppCompatActivity {


    EditText register_email,register_password;
    Button btn_signup;
    TextView login_Link;
    FirebaseAuth auth;
    FirebaseUser user;
    String URL = "http://10.0.2.2/ordering/userToken.php";

    SharedPreferences sharedPreferences;

    private static final String PREF_NAME = "ordering";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_USER_ID = "User UID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        register_email = findViewById(R.id.register_email);
        register_password = findViewById(R.id.register_password);
        btn_signup = findViewById(R.id.btn_signup);
        login_Link = findViewById(R.id.login_Link);


        login_Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegistration();


            }
        });

    }


    private void UserRegistration() {


        final String email = register_email.getText().toString();
        String password = register_password.getText().toString();

        if (email.isEmpty()){
            register_email.setError("require email Address");
            register_email.requestFocus();
            return;
        }

        if (password.isEmpty()){
            register_password.setError("password is require");
            register_password.requestFocus();
            return;
        }

        final ProgressDialog progressDialog = ProgressDialog.show(RegisterActivity.this,"Please Wait","progress",true);



        (auth.createUserWithEmailAndPassword(register_email.getText().toString(), register_password.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressDialog.dismiss();

                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registration Succesful", Toast.LENGTH_SHORT).show();

                        sharedPreferences = getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_EMAIL,email);
                        editor.putString(KEY_USER_ID,auth.getUid());
                        editor.putBoolean("activity_execute",true);
                        editor.apply();

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        RegisterToken();
                        finish();



                    }

                else {

                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                }
            }
        });
    }

    private void RegisterToken() {

        final String userid = user.getUid();
        final String email = user.getEmail();
        final String password = register_password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("token",userid);
                params.put("cust_email",email);
                params.put("cust_password",password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}
