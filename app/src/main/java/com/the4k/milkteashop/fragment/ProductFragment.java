package com.the4k.milkteashop.fragment;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.the4k.milkteashop.adapter.Productadapter;
import com.the4k.milkteashop.pojo.Productmodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.the4k.milkteashop.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    RecyclerView product_recyclerView;
    private ProgressBar progressBar;
    private Context context;
    private List<Productmodel> productmodels;
    private String URL = "http://10.0.2.2/ordering/product.php";
    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        product_recyclerView = view.findViewById(R.id.product_recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        productmodels = new ArrayList<>();
        productBind();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        product_recyclerView.setLayoutManager(manager);

        return view;
    }

    private void productBind() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                JSONObject jsonObject = null;

                for (int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        Productmodel productmodel = new Productmodel();
                        productmodel.setProduct_id(jsonObject.getString("product_id"));
                        productmodel.setProduct_name(jsonObject.getString("product_name"));
                        productmodel.setProduct_price(jsonObject.getString("product_price"));
                        productmodel.setProduct_desc(jsonObject.getString("product_desc"));
                        productmodel.setProduct_image(jsonObject.getString("product_image"));




                        productmodels.add(productmodel);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Productadapter productadapter = new Productadapter(getActivity(),productmodels);
                product_recyclerView.setAdapter(productadapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    }

}
