package com.gcit.groceryshop;

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

public class ImageAdapter extends FirebaseRecyclerAdapter<PhotoUploadHelperClass,ImageAdapter.myviewholder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ImageAdapter(@NonNull FirebaseRecyclerOptions<PhotoUploadHelperClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull PhotoUploadHelperClass model) {
        holder.mTitle.setText(model.getTitle());
        holder.mShopName.setText(model.getShopName());
        holder.mContact.setText(model.getContact());
        Glide.with(holder.mImageView.getContext()).load(model.getPhotoUri()).into(holder.mImageView);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_image_item_list,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTitle, mShopName, mContact;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mShopName = (TextView) itemView.findViewById(R.id.shopename);
            mContact = (TextView) itemView.findViewById(R.id.contact);
        }
    }
}
