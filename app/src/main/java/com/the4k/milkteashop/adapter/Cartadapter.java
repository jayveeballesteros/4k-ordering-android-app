package com.the4k.milkteashop.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.the4k.milkteashop.pojo.Cartmodel;

import java.util.List;

import com.the4k.milkteashop.R;

public class Cartadapter extends RecyclerView.Adapter<Cartadapter.ViewHolder> {

    Context context;
    List<Cartmodel> cartmodels;
    String Delete_URL = "http://10.0.2.2/ordering/delete_cart.php";
    String product_id;


    public Cartadapter(Context context, List<Cartmodel> cartmodels) {
        this.context = context;
        this.cartmodels = cartmodels;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        holder.cart_title.setText(cartmodels.get(position).getProduct_name());
        holder.cart_price.setText(cartmodels.get(position).getProduct_price());
        Glide.with(context).load(cartmodels.get(position).getCart_imageurl()).into(holder.cart_image);

        holder.edit_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddQuantity();
            }
        });

        product_id = cartmodels.get(position).getProduct_id();
    }



    /*open dialogbox in edit quantity*/

    private void AddQuantity() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.edittext_dialog,null);
        final EditText edit_dialog = view.findViewById(R.id.edit_dialog);
        Button dialog_btn = view.findViewById(R.id.dialog_btn);
        builder.setTitle("Enter Quantity");

        dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        builder.setView(view);
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }




    @Override
    public int getItemCount() {
        return cartmodels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cart_image;
        TextView  cart_title,cart_price;
        ImageView edit_quantity;


        public ViewHolder(View itemView) {
            super(itemView);

            cart_image = itemView.findViewById(R.id.cart_image);
            cart_title = itemView.findViewById(R.id.cart_title);
            cart_price = itemView.findViewById(R.id.cart_price);

            edit_quantity = itemView.findViewById(R.id.edit_quantity);



        }


    }
}
