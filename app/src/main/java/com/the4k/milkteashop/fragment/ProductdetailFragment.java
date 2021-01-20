package com.the4k.milkteashop.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.HashMap;

import java.util.Map;



import com.the4k.milkteashop.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductdetailFragment extends Fragment {


    ImageView image;
    TextView detail_pname,detail_price,detail_desc,p_id;
    Button add_cart,buy;
    FirebaseUser firebaseUser;
    private String Buy_Now = "http://10.0.2.2/ordering/purchaseNew.php";
    TextView click_review;

    SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "ordering";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_USER_ID = "User UID";


    public ProductdetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_productdetail, container, false);

        p_id = view.findViewById(R.id.p_id);
        image = view.findViewById(R.id.image);
        detail_pname = view.findViewById(R.id.detail_pname);
        detail_price = view.findViewById(R.id.detail_price);
        detail_desc = view.findViewById(R.id.detail_desc);

        buy = view.findViewById(R.id.buy);
        click_review = view.findViewById(R.id.click_review);

        sharedPreferences = getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        binddata();



        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buynow();
            }
        });

        click_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReviewsWatch();
            }
        });

        return view;

    }

    private void ReviewsWatch() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_main,new ViewReviewFragment());
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void buynow() {

        if(firebaseUser.getUid() != null && p_id != null){

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Buy_Now, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(getActivity(),response, Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }){

                @Override
                protected Map<String, String> getParams() {
                    HashMap<String,String> params = new HashMap<>();
                    params.put("token",firebaseUser.getUid());

                    Bundle bundle = getArguments();
                    String product_id = bundle.getString("product_id");

                    params.put("product_id",product_id);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }

        else {

            Toast.makeText(getActivity(),"Error", Toast.LENGTH_SHORT).show();
        }
    }

    // add data on cart table user id get successfull but product id not get



//get data FROM Productpage
    private void binddata() {
        Bundle bundle = getArguments();

        String product_id =  bundle.getString("product_id");
        String name =  bundle.getString("product_name");
        String price = bundle.getString("price");
        String description = bundle.getString("product_desc");
        String imageurl = bundle.getString("product_image");


        p_id.setText(product_id);
        detail_pname.setText(name);
        detail_price.setText(price);
        detail_desc.setText(description);
        Glide.with(this).load(imageurl).into(image);
    }




}
