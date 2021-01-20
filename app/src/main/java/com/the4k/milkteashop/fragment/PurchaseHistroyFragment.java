package com.the4k.milkteashop.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.the4k.milkteashop.R;
import com.the4k.milkteashop.adapter.Purchaseadaptor;
import com.the4k.milkteashop.pojo.Productmodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseHistroyFragment extends Fragment {

    private List<Productmodel> productmodels;
    private String Purchase_Listing = "http://10.0.2.2/ordering/purchaseList.php";
    private RecyclerView purchase_List;


    public PurchaseHistroyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_purchase_histroy, container, false);
        purchase_List = view.findViewById(R.id.purchase_List);

        productmodels = new ArrayList<>();
        ListData();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        purchase_List.setLayoutManager(manager);



        return view;
    }

    private void ListData() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Purchase_Listing, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;

                for (int i=0 ; i<response.length() ; i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Productmodel productmodel = new Productmodel();
                        productmodel.setProduct_id(jsonObject.getString("product_id"));
                        productmodel.setProduct_name(jsonObject.getString("product_name"));
                        productmodel.setProduct_image(jsonObject.getString("product_image"));
                        productmodels.add(0,productmodel);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Purchaseadaptor purchaseadaptor = new Purchaseadaptor(productmodels, getActivity());
                purchase_List.setAdapter(purchaseadaptor);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(),error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);


    }

}
