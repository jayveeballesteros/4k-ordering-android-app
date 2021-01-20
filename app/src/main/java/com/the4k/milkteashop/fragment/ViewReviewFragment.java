package com.the4k.milkteashop.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import com.the4k.milkteashop.R;
import com.the4k.milkteashop.adapter.Reviewadaptor;
import com.the4k.milkteashop.pojo.Reviewmodel;


        public class ViewReviewFragment extends Fragment {

            RecyclerView recycler_review;
            private List<Reviewmodel> reviewmodels;
            private final String View_Review = "http://10.0.2.2/ordering/viewReview.php";

            public ViewReviewFragment() {
                // Required empty public constructor
            }


            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                // Inflate the layout for this fragment
                View view = inflater.inflate(R.layout.fragment_view_review, container, false);
                recycler_review = view.findViewById(R.id.recycler_review);
                reviewmodels = new ArrayList<>();

                ViewAllReview();

                LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                recycler_review.setLayoutManager(manager);
                return view;
            }

            private void ViewAllReview() {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(View_Review, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSONObject jsonObject = null;

                        for (int i=0;i<response.length();i++){
                            try {
                                jsonObject = response.getJSONObject(i);
                                Reviewmodel reviewmodel = new Reviewmodel();
                                reviewmodel.setName(jsonObject.getString("cust_name"));
                                reviewmodel.setUser_image(jsonObject.getString("profile_photo"));
                                reviewmodel.setRate(jsonObject.getString("rating"));
                                reviewmodel.setDate(jsonObject.getString("rating_time"));
                                reviewmodel.setReview_comment(jsonObject.getString("comment"));

                                reviewmodels.add(reviewmodel);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Reviewadaptor reviewadaptor = new Reviewadaptor(getActivity(),reviewmodels);
                        recycler_review.setAdapter(reviewadaptor);

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
