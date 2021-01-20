package com.the4k.milkteashop.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.the4k.milkteashop.adapter.Categoryadaptor;
import com.the4k.milkteashop.adapter.Dashboard_adaptor;
import com.the4k.milkteashop.pojo.Categorymodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.the4k.milkteashop.R;
import com.the4k.milkteashop.pojo.Dashboardmodel;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView brand_label,result;
    RecyclerView brand_dashboard;
    List<Dashboardmodel> dashboardmodels;
    String Dashboard_url = "http://10.0.2.2/ordering/category.php";
    //String Search_Url = "http://10.0.2.2/ordering/brandSearch.php?brand_name="+search+"";





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        brand_label = view.findViewById(R.id.brand_label);
        brand_dashboard = view.findViewById(R.id.brand_dashboard);
        dashboardmodels = new ArrayList<>();
        view_banner();
        GridLayoutManager manager = new GridLayoutManager(getActivity(),3);
        brand_dashboard.setLayoutManager(manager);


        return view;
    }



    private void view_banner() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Dashboard_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                for (int i=0;i<response.length();i++){

                    try {
                        jsonObject = response.getJSONObject(i);
                        Dashboardmodel dashboardmodel = new Dashboardmodel();
                        dashboardmodel.setName(jsonObject.getString("cat_name"));
                        dashboardmodel.setImage(jsonObject.getString("cat_image"));

                        dashboardmodels.add(dashboardmodel);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Dashboard_adaptor dashboard_adaptor = new Dashboard_adaptor(getActivity(),dashboardmodels);
                brand_dashboard.setAdapter(dashboard_adaptor);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    }

}
