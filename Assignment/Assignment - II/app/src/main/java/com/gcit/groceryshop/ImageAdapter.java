package com.gcit.groceryshop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
 
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

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
        String license = model.getLicenseNo();
        Glide.with(holder.mImageView.getContext()).load(model.getPhotoUri()).into(holder.mImageView);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.relativeLayout2.setVisibility(View.VISIBLE);
                holder.relativeLayout1.setVisibility(View.GONE);
                holder.mTitle1.setText(model.getTitle());
                holder.mShopName1.setText(model.getShopName());
                holder.mContact1.setText(model.getContact());
            }
        });
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                map.put("title", holder.mTitle1.getText().toString().trim());
                map.put("shopName", holder.mShopName1.getText().toString().trim());
                map.put("contact:", holder.mContact1.getText().toString().trim());
                FirebaseDatabase.getInstance().getReference(license).child(getRef(position).getKey()).updateChildren(map);
                holder.relativeLayout1.setVisibility(View.VISIBLE);
                holder.relativeLayout2.setVisibility(View.GONE);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String license = model.getLicenseNo();
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.mTitle.getContext());
                builder.setTitle("Delete");
                builder.setMessage("Are sure you want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child(license).child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_image_item_list,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        RelativeLayout relativeLayout1, relativeLayout2;
        ImageView mImageView;
        TextView mTitle, mShopName, mContact;
        EditText mTitle1, mShopName1, mContact1;
        Button edit, update, delete;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mShopName = (TextView) itemView.findViewById(R.id.shopename);
            mContact = (TextView) itemView.findViewById(R.id.contact);

            mTitle1 = (EditText) itemView.findViewById(R.id.title1);
            mShopName1 = (EditText) itemView.findViewById(R.id.shopename1);
            mContact1 = (EditText) itemView.findViewById(R.id.contact1);
            edit = itemView.findViewById(R.id.edit);
            update = itemView.findViewById(R.id.Update);
            delete = itemView.findViewById(R.id.delete);

            relativeLayout1 = itemView.findViewById(R.id.relative1);
            relativeLayout2 = itemView.findViewById(R.id.relative2);

            relativeLayout2.setVisibility(View.GONE);
        }
    }
}
