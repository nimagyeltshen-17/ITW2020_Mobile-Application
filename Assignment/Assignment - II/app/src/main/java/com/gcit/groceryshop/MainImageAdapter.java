package com.gcit.groceryshop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class MainImageAdapter extends FirebaseRecyclerAdapter<RegisterHelperClass,MainImageAdapter.myviewholder> {
    private Context mContext;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainImageAdapter(@NonNull FirebaseRecyclerOptions<RegisterHelperClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull RegisterHelperClass model) {

        holder.mShopName.setText(model.getFullName());
        holder.mAddress.setText(model.getAddress());
        holder.mPhoneNo.setText(model.getContact());
        Glide.with(holder.mImageView.getContext()).load(model.getPhotoUri()).into(holder.mImageView);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.mImageView.getContext(),viewShopDetailsActivity.class);
                intent.putExtra("License",model.getLicense());
                intent.putExtra("FullName",model.getFullName());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.mImageView.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mShopName, mAddress, mPhoneNo;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            mImageView = (ImageView)itemView.findViewById(R.id.image);
            mShopName = (TextView)itemView.findViewById(R.id.shopname);
            mAddress = (TextView) itemView.findViewById(R.id.address);
            mPhoneNo = (TextView) itemView.findViewById(R.id.contact);
        }
    }
}
